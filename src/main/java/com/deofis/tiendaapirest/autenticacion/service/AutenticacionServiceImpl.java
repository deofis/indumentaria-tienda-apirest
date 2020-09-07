package com.deofis.tiendaapirest.autenticacion.service;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.domain.VerificationToken;
import com.deofis.tiendaapirest.autenticacion.dto.AuthResponse;
import com.deofis.tiendaapirest.autenticacion.dto.IniciarSesionRequest;
import com.deofis.tiendaapirest.autenticacion.dto.NotificationEmail;
import com.deofis.tiendaapirest.autenticacion.dto.SignupRequest;
import com.deofis.tiendaapirest.autenticacion.exception.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.repository.UsuarioRepository;
import com.deofis.tiendaapirest.autenticacion.repository.VerificationTokenRepository;
import com.deofis.tiendaapirest.autenticacion.security.JwtProveedor;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AutenticacionServiceImpl implements AutenticacionService {

    private final UsuarioRepository usuarioRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProveedor jwtProveedor;
    private final MailService mailService;

    @Transactional
    @Override
    public void registrarse(SignupRequest signupRequest) {
        // No se puede enviar mail, y crear el usuario luego?

        Usuario usuario = Usuario.builder()
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .email(signupRequest.getEmail())
                .enabled(false)
                .fechaCreacion(new Date())
                .build();

        try {
            this.usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new AutenticacionException("Ya existe el usuario: " + signupRequest.getUsername());
        }



        String token = this.generateVerificationToken(usuario);
        NotificationEmail notificationEmail = new NotificationEmail();
        notificationEmail.setSubject("Porfavor, active su cuenta.");
        notificationEmail.setRecipient(usuario.getEmail());
        notificationEmail.setBody("Gracias por registrarse a E-COMMERCE GENÉRICO! Porfavor, verifique su cuenta " +
                "haciendo click en el enlace de aqui abajo:\n");
        this.mailService.sendEmail(notificationEmail, "http://localhost:8080/api/auth/accountVerification/" + token);
    }

    // Observador para verificar el paso del tiempo --> Si paso el expire date y el
    // usuario no se dio de alta: Eliminar usuario de la BD.
    @Override
    public void verificarCuenta(String token) {
        VerificationToken verificationToken = this.verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new AutenticacionException("Token inválido."));

        fetchUserAndEnable(verificationToken);
    }

    @Override
    public AuthResponse iniciarSesion(IniciarSesionRequest iniciarSesionRequest) {
        Authentication authenticate = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(iniciarSesionRequest.getUsername(),
                        iniciarSesionRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwtToken = this.jwtProveedor.generateToken(authenticate);

        return AuthResponse.builder()
                .authToken(jwtToken)
                .username(iniciarSesionRequest.getUsername())
                .expiraEn(new Date(new Date().getTime() + jwtProveedor.getExpirationInMillis()))
                .build();
    }

    /**
     * Generates a token for account verification.
     * @param usuario user signing up.
     * @return String with the token for acc verification.
     */
    private String generateVerificationToken(Usuario usuario) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .usuario(usuario)
                .build();

        // Setear expiración.

        this.verificationTokenRepository.save(verificationToken);
        return token;
    }

    /**
     * Enables an user account.
     * @param verificationToken String token matching the username to enable.
     */
    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) {

        String username = verificationToken.getUsuario().getUsername();

        Usuario usuario = this.usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new AutenticacionException("No se encontro el usuario: " + username));
        usuario.setEnabled(true);
        this.usuarioRepository.save(usuario);
        // Luego de verificar la cuenta, se borra el token para borrar data basura.
        this.verificationTokenRepository.delete(verificationToken);
    }
}

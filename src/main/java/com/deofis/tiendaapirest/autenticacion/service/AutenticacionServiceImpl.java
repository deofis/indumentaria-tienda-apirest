package com.deofis.tiendaapirest.autenticacion.service;

import com.deofis.tiendaapirest.autenticacion.domain.Rol;
import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.domain.VerificationToken;
import com.deofis.tiendaapirest.autenticacion.dto.*;
import com.deofis.tiendaapirest.autenticacion.exception.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.repository.RolRepository;
import com.deofis.tiendaapirest.autenticacion.repository.UsuarioRepository;
import com.deofis.tiendaapirest.autenticacion.security.JwtProveedor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class AutenticacionServiceImpl implements AutenticacionService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    //private final VerificationTokenRepository verificationTokenRepository;
    private final VerificationTokenService verificationTokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProveedor jwtProveedor;
    private final MailService mailService;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    @Override
    public void registrarse(SignupRequest signupRequest) {
        // No se puede enviar mail, y crear el usuario luego?

        Rol user = this.rolRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new AutenticacionException("ROLE_USER no cargado."));

        if (this.usuarioRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new AutenticacionException("Ya existe el usuario con el email: " + signupRequest.getEmail() +".\n" +
                    "Si no activó su cuenta, porfavor revise su bandeja de entradas.");
        }

        if (signupRequest.getPassword().length() <= 7) {
            throw new AutenticacionException("La contraseña debe tener al menos 8 caracteres.");
        }


        Usuario usuario = Usuario.builder()
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .enabled(false)
                .fechaCreacion(new Date())
                .rol(user)
                .build();

        try {
            this.usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new AutenticacionException("Excepción al registrar usuario: " + signupRequest.getEmail());
        }

        String token = this.verificationTokenService.generarVerificationToken(usuario);
        NotificationEmail notificationEmail = new NotificationEmail();
        String mailBody = "Gracias por registrarse a E-COMMERCE GENÉRICO! Porfavor, verifique su cuenta" +
                "                \"haciendo click en el enlace de aqui abajo:\n";

        notificationEmail.setSubject("Porfavor, active su cuenta.");
        notificationEmail.setRecipient(usuario.getEmail());
        notificationEmail.setBody(mailBody);
        this.mailService.sendEmail(notificationEmail, "http://localhost:8080/api/auth/accountVerification/" + token);
    }

    // Observador para verificar el paso del tiempo --> Si paso el expire date y el
    // usuario no se dio de alta: Eliminar usuario de la BD.
    @Override
    public void verificarCuenta(String token) {
        VerificationToken verificationToken = this.verificationTokenService.getVerificationToken(token);

        fetchUserAndEnable(verificationToken);
    }

    @Override
    public AuthResponse iniciarSesion(IniciarSesionRequest iniciarSesionRequest) {
        Authentication authenticate = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(iniciarSesionRequest.getEmail(),
                        iniciarSesionRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwtToken = this.jwtProveedor.generateToken(authenticate);

        return AuthResponse.builder()
                .authToken(jwtToken)
                .userEmail(iniciarSesionRequest.getEmail())
                .refreshToken(this.refreshTokenService.generarRefreshToken().getToken())
                .expiraEn(new Date(new Date().getTime() + jwtProveedor.getExpirationInMillis()))
                .build();
    }

    @Override
    public void cerrarSesion(RefreshTokenRequest refreshTokenRequest) {
        if (this.estaLogueado()) {
            this.refreshTokenService.eliminarRefreshToken(refreshTokenRequest.getRefreshToken());
            SecurityContextHolder.getContext().setAuthentication(null);
        } else {
            log.info("NO LOGUEADO");
            throw new AutenticacionException("Usuario no logueado en el sistema");
        }
    }

    @Override
    public AuthResponse refrescarToken(RefreshTokenRequest refreshTokenRequest) {
        this.refreshTokenService.validarRefreshToken(refreshTokenRequest.getRefreshToken());
        String jwt = this.jwtProveedor.generateTokenWithUsername(refreshTokenRequest.getUserEmail());

        return AuthResponse.builder()
                .authToken(jwt)
                .userEmail(refreshTokenRequest.getUserEmail())
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiraEn(new Date(new Date().getTime() + this.jwtProveedor.getExpirationInMillis()))
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario getUsuarioActual() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.usuarioRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new AutenticacionException("Usuario no encontrado: " +
                        principal.getUsername()));
    }

    @Override
    public boolean estaLogueado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }


    /**
     * Genenra un token para la verificación de cuenta.
     * @param usuario user que se está registrando.
     * @return String del token de verificación.
     */
    /*
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
     */


    /**
     * Habilita una cuenta de usuario que hace match con el token de verificación.
     * @param verificationToken VerificationToken objeto token con los datos del usuario y
     *                          el token de verificación.
     */
    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) {

        String userEmail = verificationToken.getUsuario().getEmail();

        Usuario usuario = this.usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AutenticacionException("No se encontro el usuario: " + userEmail));
        usuario.setEnabled(true);
        this.usuarioRepository.save(usuario);
        // Luego de verificar la cuenta, se borra el token para borrar data basura.
        this.verificationTokenService.delete(verificationToken);
    }
}

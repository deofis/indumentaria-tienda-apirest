package com.deofis.tiendaapirest.autenticacion.service;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.dto.CambiarPasswordRequest;
import com.deofis.tiendaapirest.autenticacion.dto.NotificationEmail;
import com.deofis.tiendaapirest.autenticacion.exception.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class PasswordServiceImpl implements PasswordService {
    private static final String URL_REDIRIGIR = "http://localhost:4200/recuperar-password";

    private final AutenticacionService autenticacionService;
    private final UsuarioRepository usuarioRepository;
    private final VerificationTokenService verificationTokenService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Transactional
    @Override
    public Usuario cambiarPassword(CambiarPasswordRequest passwordRequest) {

        if (this.autenticacionService.estaLogueado()) {
            log.info("LOGUEADO");

            Usuario usuario = this.autenticacionService.getUsuarioActual();
            usuario.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
            return this.usuarioRepository.save(usuario);
        } else {
            log.info("NO LOGUEADO");
            throw new AutenticacionException("Usuario no logueado en el sistema");
        }

    }

    @Transactional
    @Override
    public void recuperarPassword(String userEmail) {
        Usuario usuario = this.usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AutenticacionException("No se encontro el usuario: " + userEmail));

        String token = this.verificationTokenService.generarVerificationToken(usuario);

        NotificationEmail notificationEmail = new NotificationEmail();
        String mailBody = "Se ha registrado un intento de reinicio de contraseña. Si fue usted quien lo solicitó" +
                "\nporfavor haga click en el enlace de aquí abajo. Si no fue usted, ignore este correo";
        notificationEmail.setSubject("Recuperar contraseña");
        notificationEmail.setRecipient(userEmail);
        notificationEmail.setBody(mailBody);

        // En futuro la URL redirige a una página estática del front del dominio.
        this.mailService.sendEmail(notificationEmail, URL_REDIRIGIR);
    }
}

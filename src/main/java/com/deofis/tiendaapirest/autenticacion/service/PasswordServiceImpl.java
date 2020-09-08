package com.deofis.tiendaapirest.autenticacion.service;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.dto.CambiarPasswordRequest;
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

    private final AutenticacionService autenticacionService;
    private final UsuarioRepository usuarioRepository;
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

    @Override
    public void recuperarPassword() {

    }
}

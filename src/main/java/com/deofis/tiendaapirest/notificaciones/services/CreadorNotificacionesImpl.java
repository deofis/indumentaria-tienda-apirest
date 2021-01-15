package com.deofis.tiendaapirest.notificaciones.services;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.exceptions.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.repositories.UsuarioRepository;
import com.deofis.tiendaapirest.notificaciones.dto.Notificacion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CreadorNotificacionesImpl implements CreadorNotificaciones {

    private final UsuarioRepository usuarioRepository;
    private final String clientUrl;

    @Override
    public Notificacion build(String titulo, String contenido, String actionUrl, String email) {
        Usuario usuario = this.usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new AutenticacionException("Usuario no existente con email" + email));
        log.info(this.clientUrl);

        return Notificacion.builder()
                .titulo(titulo)
                .contenido(contenido)
                .categoria("new_message")
                .url(this.clientUrl.concat(actionUrl))
                .usuario(usuario).build();
    }
}

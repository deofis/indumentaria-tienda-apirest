package com.deofis.tiendaapirest.notificaciones.dto;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notificacion {
    private String titulo;
    private String contenido;
    private String categoria;
    private String url;
    private Usuario usuario;
}

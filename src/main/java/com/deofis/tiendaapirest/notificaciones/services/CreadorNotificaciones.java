package com.deofis.tiendaapirest.notificaciones.services;

import com.deofis.tiendaapirest.notificaciones.dto.Notificacion;

/**
 * Servicio que se encarga de construir una notificación a partir de un objeto
 * y el email del usuario a quien pertenece la notificación.
 */
public interface CreadorNotificaciones {
    /**
     * Construye una notificación.
     * @param titulo String título de la notificación a construir.
     * @param contenido String contenido del cuerpo de la notificación a construir.
     * @param actionUrl String url asignada a la notificación.
     * @param email String email del usuario relacionado a la notificación.
     * @return {@link Notificacion} creada.
     */
    Notificacion build(String titulo, String contenido, String actionUrl, String email);
}

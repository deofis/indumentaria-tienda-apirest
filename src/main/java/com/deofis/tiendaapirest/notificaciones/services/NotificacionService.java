package com.deofis.tiendaapirest.notificaciones.services;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;

/**
 * Servicio que se encarga de generar y enviar las notificaciones según el evento que
 * lo requiera, por ej.: Nueva operación registrada.
 * <br>
 * La implementación actual de este servicio utiliza una API de notificaciones llamada
 * MagicBell, que provee servicio de notificaciones instantaneas para utilizar, que
 * relaciona a usuarios con sus notificaciones.
 */
public interface NotificacionService {
    /**
     * Se encarga de enviar una notificación al usuario correspondiente, informando
     * que una nueva operación (compra) se registró con su usuario.
     * @param operacion {@link Operacion} nueva registrada. Se utiliza para popular los datos
     *                                   de la notificación a enviar.
     * @param email String con el email del usuario que recibirá la notificación sobre la nueva
     *              compra.
     */
    void enviarNotificacion(Operacion operacion, String email);
}

package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;

import java.util.List;

/**
 * Este servicio se encarga de la lógica para el manejo de {@link Operacion}es desde el lado
 * del administrador del sistema, es decir, viendo las {@link Operacion}es como VENTAS.
 */

public interface VentaService {

    /**
     * Como administrador quiero obtener un listado con todas las ventas que fueron realizadas
     * en el sistema.
     * @return List con las operaciones registradas, ordenadas por fecha.
     */
    List<Operacion> listarVentas();

    /**
     * Como administrador quiero ver una venta en particular.
     * @param nroOperacion Long numero de operacion correspondiente a ver.
     * @return Operacion solicitada.
     */
    Operacion obtenerVenta(Long nroOperacion);
}

package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;

import java.util.List;

/**
 * Este servicio se encarga de implementar la lógica para obtener las compras de
 * un perfil.
 */

public interface CompraService {

    /**
     * Obtener un listado con el historial de compras del cliente del perfil ordenado por fecha de
     * menor a mayor.
     * @return List operaciones de compra del perfil.
     */
    List<Operacion> historialCompras();

    /**
     * Obtiene una operación de compra.
     * @param nroOperacion Long numero de operación.
     * @return Operacion.
     */
    Operacion verCompra(Long nroOperacion);
}

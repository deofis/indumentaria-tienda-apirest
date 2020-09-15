package com.deofis.tiendaapirest.operacion.services;

import com.deofis.tiendaapirest.operacion.domain.Operacion;

import java.util.List;

public interface OperacionService {

    /**
     * Registrar una nueva operación con los datos: Cliente, Productos y cantidades, fecha de operación
     * y el estado inicial PENDING.
     * @param operacion Operacion a registrar.
     * @return Operacion ya guardada en la base de datos y completada.
     */
    Operacion registrar(Operacion operacion);

    /**
     * Como administrador quiero obtener un listado con todas las ventas que fueron realizadas
     * en el sistema.
     * @return List con las operaciones registradas, ordenadas por fecha.
     */
    List<Operacion> listarVentas();
}

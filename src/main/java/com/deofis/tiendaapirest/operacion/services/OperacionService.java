package com.deofis.tiendaapirest.operacion.services;

import com.deofis.tiendaapirest.operacion.domain.Operacion;

import java.util.List;

public interface OperacionService {

    /**
     * Registra, como usuario cliente,  una nueva operación con los datos:
     * Cliente, Productos y cantidades, fecha de operación
     * y el estado inicial PENDING.
     * @param operacion Operacion a registrar.
     * @return Operacion ya guardada en la base de datos y completada.
     */
    Operacion registrarNuevaOperacion(Operacion operacion);

    /**
     * Registra, por parte de un usuario administrador,  el envio de una operación compra/venta hacia el cliente.
     * @param nroOperacion Long con el nro de operación correspondiente al envio.
     * @return Operación con su nuevo estado (SENT)
     */
    Operacion registrarEnvio(Long nroOperacion);

    /**
     * Registra, por parte del usuario cliente, el recibo de los productos solicitados en la operacion
     * @param nroOperacion Long nro de operación correspondiente al recibo.
     * @return Operacion con su nuevo estado (RECEIVED)
     */
    Operacion registrarRecibo(Long nroOperacion);

    /**
     * Cancela, por parte del usuario cliente, la cancelación de la operación.
     * @param nroOperacion Long nro de operacion a cancelar.
     */
    Operacion cancelar(Long nroOperacion);

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

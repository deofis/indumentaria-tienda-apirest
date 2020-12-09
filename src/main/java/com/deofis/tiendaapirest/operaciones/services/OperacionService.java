package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;

/**
 * Servicio que se encarga de la lógica de las {@link Operacion}es y sus transacciones: Registrar un nuevo pedido, y
 * registrar las distintas transiciones de Estados de la {@link Operacion}.
 */

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
}

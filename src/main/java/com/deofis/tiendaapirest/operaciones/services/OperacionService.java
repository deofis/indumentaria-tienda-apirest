package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.globalservices.CrudService;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.pagos.factory.OperacionPagoInfo;

/**
 * Servicio que se encarga de la lógica de las {@link Operacion}es y sus transacciones: Registrar un nuevo pedido, y
 * registrar las distintas transiciones de Estados de la {@link Operacion}.
 */

public interface OperacionService extends CrudService<Operacion, Long> {

    /**
     * Registra, como usuario cliente,  una nueva operación con los datos:
     * Cliente, Productos y cantidades, fecha de operación
     * y el estado inicial PENDING.
     * <br>
     * Crea el pago a realizar y lo devuelve con su información correspondiente.
     * @param operacion Operacion a registrar.
     * @return OperacionPagoInfo con la información necesaria para completar el pago.
     */
    OperacionPagoInfo registrarNuevaOperacion(Operacion operacion);

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
     * Obtiene una {@link Operacion} a través de su número de operación.
     * @param nroOperacion Long nro de operación.
     * @return Operacion.
     */
    Operacion findById(Long nroOperacion);
}

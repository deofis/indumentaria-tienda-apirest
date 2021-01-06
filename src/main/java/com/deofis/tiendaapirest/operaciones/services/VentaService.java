package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;

import java.util.Date;
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
     * Lista todas las ventas que se encuentran en el estado pendiente de pago.
     * @return List operaciones PAYMENT_PENDING.
     */
    List<Operacion> ventasPendientePago();

    /**
     * Lista todas las ventas que se encuentran en el estado pago completado.
     * @return List operaciones PAYMENT_DONE.
     */
    List<Operacion> ventasCompletadoPago();

    /**
     * Lista todas las ventas que se encuentran en el estado enviada.
     * @return List operaciones SENT.
     */
    List<Operacion> ventasEnviadas();

    /**
     * Lista todas las ventas que se encuentran en el estado recibida.
     * @return List operaciones RECEIVED.
     */
    List<Operacion> ventasRecibidas();

    /**
     * Lista todas las ventas que se encuentran en el estado cancelada.
     * @return List operaciones CANCELLED.
     */
    List<Operacion> ventasCanceladas();

    /**
     * Obtiene todas las ventas que se encuentren dentro de las fechas requeridas.
     * @param fechaDesde Date fecha inicio.
     * @param fechaHasta Date fecha fin.
     * @return List operaciones en la fechas requeridas.
     */
    List<Operacion> ventasFecha(Date fechaDesde, Date fechaHasta);

    List<Operacion> ventasFechaYEstado(String estado, Date fechaDesde, Date fechaHasta);

    /**
     * Como administrador quiero ver una venta en particular.
     * @param nroOperacion Long numero de operacion correspondiente a ver.
     * @return Operacion solicitada.
     */
    Operacion obtenerVenta(Long nroOperacion);
}

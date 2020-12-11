package com.deofis.tiendaapirest.pagos.services.paypal;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.pagos.dto.OrderPayload;
import com.deofis.tiendaapirest.pagos.dto.PaymentPayload;
import com.deofis.tiendaapirest.pagos.services.strategy.PagoStrategy;

/**
 * Servicio que se encarga de crear y completar pagos de una {@link Operacion} a través
 * de la API de PayPal.
 */

@Deprecated
public interface PayPalStrategyDeprecated extends PagoStrategy {

    /**
     * Crea una orden de PayPal y genera una url donde se puede aprobar el pago, siendo usuario de paypal
     * @param operacion Operacion registrada que estará asociada al pago, para calcular el total a pagar.
     * @return OrderPayload información enviada por PayPal: estado y id de la orden, y la url para aprobar
     * el pago.
     */
    OrderPayload crearOrder(Operacion operacion);

    /**
     * Captura la orden a traves de su id, esto significa que se llamará a la API para completar el pago
     * de la orden asociada.
     * @param orderId String id de la order enviada por la API de PayPal (llamada token por ellos)
     * @return PaymentPayload información enviada por PayPal: estado y id de la orden, datos del comprador
     * y datos acerca de los montos de pago.
     */
    PaymentPayload capturarOrder(String orderId);
}

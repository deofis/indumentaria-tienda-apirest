package com.deofis.tiendaapirest.checkout.services;

import com.deofis.tiendaapirest.pagos.factory.OperacionPagoInfo;

/**
 * Servicio que se encarga de ejecutar el checkout de una operación, completando el pago
 * de la misma.
 */

public interface CheckoutService {

    OperacionPagoInfo ejecutarCheckout(Long nroOperacion);
}

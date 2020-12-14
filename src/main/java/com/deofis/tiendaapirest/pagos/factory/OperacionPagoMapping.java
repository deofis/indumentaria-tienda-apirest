package com.deofis.tiendaapirest.pagos.factory;

import com.deofis.tiendaapirest.pagos.domain.OperacionPago;

/**
 * Esta clase se encarga de realizar el mapeo entre {@link OperacionPagoInfo}, que contiene
 * los datos al crear el pago; y {@link OperacionPago}, que es la entidad que persiste en la
 * base de datos, asociada a una operación.
 */

public interface OperacionPagoMapping {

    OperacionPago mapToOperacionPago(OperacionPagoInfo pagoInfo);

}

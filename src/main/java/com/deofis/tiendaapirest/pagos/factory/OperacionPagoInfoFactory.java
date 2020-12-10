package com.deofis.tiendaapirest.pagos.factory;

import com.deofis.tiendaapirest.operaciones.exceptions.OperacionException;
import com.deofis.tiendaapirest.pagos.domain.MedioPagoEnum;

import java.util.Map;

public class OperacionPagoInfoFactory {

    public static OperacionPagoInfo getOperacionPagoInfo(String medioPago, Map<String, Object> atributos) {
        if (medioPago.equalsIgnoreCase(String.valueOf(MedioPagoEnum.EFECTIVO))) {
            return new EfectivoPagoInfo(atributos);
        } else if (medioPago.equalsIgnoreCase(String.valueOf(MedioPagoEnum.PAYPAL))) {
            return new PayPalPagoInfo(atributos);
        } else {
            throw new OperacionException("Medio de pago " + medioPago + " aun" +
                    " no ha sido implementado");
        }
    }
}

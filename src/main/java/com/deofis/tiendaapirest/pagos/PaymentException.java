package com.deofis.tiendaapirest.pagos;

public class PaymentException extends RuntimeException {
    public PaymentException(String exMensaje) {
        super(exMensaje);
    }
}

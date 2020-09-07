package com.deofis.tiendaapirest.autenticacion.exception;

public class TokenException extends RuntimeException {
    public TokenException(String exMensaje) {
        super(exMensaje);
    }
}

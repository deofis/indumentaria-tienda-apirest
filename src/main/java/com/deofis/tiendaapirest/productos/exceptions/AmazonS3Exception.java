package com.deofis.tiendaapirest.productos.exceptions;

public class AmazonS3Exception extends RuntimeException {
    public AmazonS3Exception(String exMensaje) {
        super(exMensaje);
    }
}

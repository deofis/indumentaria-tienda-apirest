package com.deofis.tiendaapirest.operaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperacionDTO {

    private Long nroOperacion;
    private String estado;
    private Date fechaOperacion;
    private Date fechaEnviada;
    private Date fechaRecibida;
    private String cliente;
    private String formaPago;
    private Double total;
}

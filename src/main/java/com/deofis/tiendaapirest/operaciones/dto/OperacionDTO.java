package com.deofis.tiendaapirest.operaciones.dto;

import com.deofis.tiendaapirest.pagos.domain.MedioPagoEnum;
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
    private MedioPagoEnum medioPago;
    private Double total;
}

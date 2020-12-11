package com.deofis.tiendaapirest.pagos.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "operacion_pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperacionPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private Long nroOperacion;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_pagado")
    private Date fechaPagado;

    private String approveUrl;

    private String totalBruto;

    private String totalNeto;

    private String fee;

    private String payerId;

    private String payerEmail;

    private String payerFullName;
}

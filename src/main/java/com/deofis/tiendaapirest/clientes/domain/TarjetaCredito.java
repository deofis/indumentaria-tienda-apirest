package com.deofis.tiendaapirest.clientes.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Entidad para el manejo de las tarjetas de crédito.
 * A refinar cuando se trabaje sobre el modulo de compras.
 */

@Entity
@Data
@Table(name = "tarjetas_credito")
public class TarjetaCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numero;
    private String apellido;
    private String nombre;
    private String vencimiento;
    @Column(name = "codigo_seguridad")
    private Integer codigoSeguridad;
}

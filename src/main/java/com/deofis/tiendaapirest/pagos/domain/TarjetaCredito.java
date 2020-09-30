package com.deofis.tiendaapirest.pagos.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entidad para el manejo de las tarjetas de crédito.
 * A refinar cuando se trabaje sobre el modulo de compras.
 */

@Entity
@Data
@Table(name = "tarjetas_credito")
public class TarjetaCredito implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numero;
    private String apellido;
    private String nombre;
    private String vencimiento;
    private Integer cvc;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_tarjeta_id")
    private MarcaTarjeta marcaTarjeta;
}

package com.deofis.tiendaapirest.clientes.domain;

import com.deofis.tiendaapirest.pagos.domain.TarjetaCredito;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre es obligatorio.")
    private String nombre;

    @NotNull(message = "El apellido es obligatorio.")
    private String apellido;

    private String email;

    private String telefono;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Direccion direccion;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tarjeta_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TarjetaCredito tarjetaCredito;
}

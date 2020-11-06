package com.deofis.tiendaapirest.clientes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "direcciones")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id")
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    private Ciudad ciudad;

    private String calle;

    @Column(name = "numero_calle")
    private String numeroCalle;

    private String piso;

    @Column(name = "codigo_postal")
    private String codigoPostal;
}

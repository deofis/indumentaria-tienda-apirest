package com.deofis.tiendaapirest.clientes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "direcciones")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id")
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    private Estado estado;
    private String calle;
    @Column(name = "numero_calle")
    private String numeroCalle;
    private String piso;
    private String departamento;
    @Column(name = "codigo_postal")
    private String codigoPostal;
}

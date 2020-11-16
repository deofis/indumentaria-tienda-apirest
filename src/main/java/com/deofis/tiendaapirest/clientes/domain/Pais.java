package com.deofis.tiendaapirest.clientes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "countries")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pais implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    private Long id;
    private String nombre;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pais_id")
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    private List<Estado> estados;
}

package com.deofis.tiendaapirest.productos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "propiedades")
@Data
@AllArgsConstructor
@Builder
public class Propiedad implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "propiedad_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<ValorPropiedad> valores;

    public Propiedad() {
        this.valores = new ArrayList<>();
    }
}

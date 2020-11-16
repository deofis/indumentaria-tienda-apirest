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
@Table(name = "subcategorias")
@Data
@AllArgsConstructor
@Builder
public class Subcategoria implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String codigo;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subcategoria_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<PropiedadProducto> propiedades;

    public Subcategoria() {
        this.propiedades = new ArrayList<>();
    }
}

package com.deofis.tiendaapirest.productos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "producto_propiedades")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropiedadProducto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_propiedad_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<ValorPropiedadProducto> valores;
}

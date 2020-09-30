package com.deofis.tiendaapirest.productos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "productos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre del producto es obligatorio.")
    private String nombre;

    @NotNull(message = "La descripción del producto es obligatoria.")
    private String descripcion;

    @NotNull(message = "El precio del producto es obligatorio.")
    private Double precio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    private String foto;

    private boolean activo;

    private boolean destacado;

    @NotNull(message = "La categoría del producto es obligatoria.")
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Categoria categoria;

    @NotNull(message = "La marca del producto es obligatoria.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Marca marca;

    @NotNull
    private Integer stock;

    private String color;

    private String talle;

    private String peso;

    @NotNull(message = "La unidad de medida del producto es obligatoria.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad_medida_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UnidadMedida unidadMedida;
}

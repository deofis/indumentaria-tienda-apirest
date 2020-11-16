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
import java.util.List;

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

    private Double precioOferta;

    @NotNull(message = "La disponibilidad inicial del producto es obligatorio.")
    private Integer disponibilidad;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    private String foto;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id")
    private List<Imagen> imagenes;

    private boolean activo;

    private boolean destacado;

    @NotNull(message = "La subcategoria del producto es obligatoria.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategoria_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Subcategoria subcategoria;

    @NotNull(message = "La marca del producto es obligatoria.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Marca marca;

    @NotNull(message = "La unidad de medida del producto es obligatoria.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad_medida_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UnidadMedida unidadMedida;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "productos_x_propiedades",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_propiedad_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"producto_id", "producto_propiedad_id"})})
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<PropiedadProducto> propiedades;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "default_sku_id")
    @JsonIgnoreProperties(value = {"producto", "defaultProducto" , "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private Sku defaultSku;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"producto", "defaultProducto", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private List<Sku> skus;
}

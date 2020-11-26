package com.deofis.tiendaapirest.productos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "skus")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sku implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private Double precio;

    private Double precioOferta;

    private Integer disponibilidad;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "foto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Imagen foto;

    private String valoresData;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "sku_x_valores_propiedades_producto",
            joinColumns = @JoinColumn(name = "sku_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_valores_propiedad_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"sku_id", "producto_valores_propiedad_id"})})
    private List<ValorPropiedadProducto> valores;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "default_producto_id")
    @JsonIgnoreProperties(value = {"skus", "defaultSku" , "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @ToString.Exclude
    private Producto defaultProducto;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties(value = {"skus", "defaultSku" , "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @ToString.Exclude
    private Producto producto;
}

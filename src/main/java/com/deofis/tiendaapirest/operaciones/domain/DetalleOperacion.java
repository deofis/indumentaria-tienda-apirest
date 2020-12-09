package com.deofis.tiendaapirest.operaciones.domain;

import com.deofis.tiendaapirest.productos.domain.Sku;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "operacion_detalles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleOperacion implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Sku sku;

    private Integer cantidad;

    @Column(name = "precio_venta")
    private Double precioVenta;

    private Double subtotal;
}

package com.deofis.tiendaapirest.operacion.domain;

import com.deofis.tiendaapirest.productos.domain.Producto;
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
    @JoinColumn(name = "producto_id")
    private Producto producto;
    private Integer cantidad;
    private Double precioVenta;
    private Double subTotal;
}

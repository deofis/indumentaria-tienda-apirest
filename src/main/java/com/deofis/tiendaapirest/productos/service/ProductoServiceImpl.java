package com.deofis.tiendaapirest.productos.service;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public Producto registrarProducto(Producto producto) {

        Producto nuevoProducto = Producto.builder()
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .fechaCreacion(new Date())
                .imagen(null)
                .tieneDescuento(producto.getTieneDescuento())
                .esDestacado(producto.getEsDestacado())
                .build();

        return this.productoRepository.save(nuevoProducto);
    }

    /**
     * Lista todos los productos en la Base de Datos ordenados por nombre desc.
     * @return List de Productos ordenados por nombre.
     */
    @Override
    public List<Producto> obtenerProductos() {
        return this.productoRepository.findAllByOrderByNombreDesc();
    }
}

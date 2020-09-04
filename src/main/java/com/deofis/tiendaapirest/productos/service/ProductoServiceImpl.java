package com.deofis.tiendaapirest.productos.service;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.UnidadMedida;
import com.deofis.tiendaapirest.productos.exception.ProductoException;
import com.deofis.tiendaapirest.productos.repository.ProductoRepository;
import com.deofis.tiendaapirest.productos.repository.UnidadMedidaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final UnidadMedidaRepository unidadMedidaRepository;

    /* Issue --> Es mejor buscar objetos en la bd antes que asignarlos directamente, no porque tire
    * error, sino para el manejo de excepciones por si el front intenta seleccionar un objeto
    * no existente
    * FIX --> para categoria, marca y unidad de medida, buscar objetos en bd antes de asignarlos*/
    @Override
    @Transactional
    public Producto crear(Producto producto) {
        Producto nuevoProducto = Producto.builder()
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .fechaCreacion(new Date())
                .foto(null)
                .activo(true)
                .destacado(true)
                .favorito(false)
                .categoria(producto.getCategoria())
                .marca(producto.getMarca())
                .stock(producto.getStock())
                .color(producto.getColor())
                .talle(producto.getTalle())
                .peso(producto.getPeso())
                .unidadMedida(producto.getUnidadMedida())
                .build();

        return this.productoRepository.save(nuevoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerProductos() {
        return this.productoRepository.findAllByOrderByNombreAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerProducto(Long id) {
        return this.productoRepository.findById(id)
                .orElseThrow(() -> new ProductoException("Producto no existente con ID: " + id));
    }

    @Override
    @Transactional
    public Producto actualizar(Producto producto, Long id) {

        Producto productoActual = this.obtenerProducto(id);

        productoActual.setNombre(producto.getNombre());
        productoActual.setDescripcion(producto.getDescripcion());
        productoActual.setActivo(producto.isActivo());
        productoActual.setPrecio(producto.getPrecio());
        productoActual.setFechaCreacion(producto.getFechaCreacion());
        productoActual.setDestacado(producto.isDestacado());
        productoActual.setCategoria(producto.getCategoria());
        productoActual.setMarca(producto.getMarca());
        productoActual.setStock(producto.getStock());
        productoActual.setColor(producto.getColor());
        productoActual.setTalle(producto.getTalle());
        productoActual.setPeso(producto.getPeso());
        productoActual.setUnidadMedida(producto.getUnidadMedida());

        return this.productoRepository.save(productoActual);
    }

    @Override
    @Transactional
    public void darDeBaja(Producto producto, Long id) {
        Producto productoActual = this.obtenerProducto(id);

        if (!productoActual.isActivo()) {
            throw new ProductoException("Este producto ya está dado de baja");
        }

        productoActual.setActivo(false);
        this.productoRepository.save(productoActual);
    }

    @Override
    @Transactional
    public void darDeAlta(Producto producto, Long id) {
        Producto productoActual = this.obtenerProducto(id);

        if (productoActual.isActivo()) {
            throw new ProductoException("Este producto ya está activo");
        }

        productoActual.setActivo(true);
        this.productoRepository.save(productoActual);
    }

    @Override
    @Transactional
    public void destacar(Producto producto, Long id) {
        Producto productoActual = this.obtenerProducto(id);

        productoActual.setDestacado(!producto.isDestacado());
        this.productoRepository.save(productoActual);
    }

    @Override
    @Transactional
    public void marcarFavorito(Producto producto, Long id) {
        Producto productoActual = this.obtenerProducto(id);

        productoActual.setFavorito(!producto.isFavorito());
        this.productoRepository.save(productoActual);
    }

    @Override
    public List<UnidadMedida> obtenerUnidadesMedida() {
        return this.unidadMedidaRepository.findAll();
    }


}

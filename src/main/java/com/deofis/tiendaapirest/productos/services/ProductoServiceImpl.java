package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.Sku;
import com.deofis.tiendaapirest.productos.domain.UnidadMedida;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import com.deofis.tiendaapirest.productos.repositories.UnidadMedidaRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final UnidadMedidaRepository unidadMedidaRepository;

    @Override
    @Transactional
    public Producto crearProducto(Producto producto) {
        List<PropiedadProducto> propiedades = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(producto.getPropiedades())) propiedades.addAll(producto.getPropiedades());

        Producto nuevoProducto = Producto.builder()
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .fechaCreacion(new Date())
                .foto(null)
                .activo(true)
                .destacado(producto.isDestacado())
                .subcategoria(producto.getSubcategoria())
                .marca(producto.getMarca())
                .disponibilidadGeneral(producto.getDisponibilidadGeneral())
                .unidadMedida(producto.getUnidadMedida())
                .propiedades(propiedades)
                .skus(new ArrayList<>())
                .build();

        nuevoProducto.setDefaultSku(Sku.builder()
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .precioOferta(producto.getPrecioOferta())
                .disponibilidad(producto.getDisponibilidadGeneral())
                .valores(null)
                .producto(null)
                .valoresData(null)
                .defaultProducto(nuevoProducto).build());

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
    public Producto actualizarProducto(Producto producto, Long id) {

        Producto productoActual = this.obtenerProducto(id);

        productoActual.setNombre(producto.getNombre());
        productoActual.setDescripcion(producto.getDescripcion());
        productoActual.setPrecio(producto.getPrecio());
        productoActual.setSubcategoria(producto.getSubcategoria());
        productoActual.setMarca(producto.getMarca());
        productoActual.setDisponibilidadGeneral(producto.getDisponibilidadGeneral());
        productoActual.setUnidadMedida(producto.getUnidadMedida());
        productoActual.getDefaultSku().setDisponibilidad(producto.getDisponibilidadGeneral());

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

    @Transactional(readOnly = true)
    @Override
    public List<UnidadMedida> obtenerUnidadesMedida() {
        return this.unidadMedidaRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PropiedadProducto> obtenerPropiedadesDeProducto(Long productoId) {
        Producto producto = this.obtenerProducto(productoId);

        return producto.getPropiedades();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Sku> obtenerSkusProducto(Long productoId) {
        Producto producto = this.obtenerProducto(productoId);

        return producto.getSkus();
    }
}

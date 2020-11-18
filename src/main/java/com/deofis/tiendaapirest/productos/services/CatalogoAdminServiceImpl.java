package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.*;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import com.deofis.tiendaapirest.productos.repositories.SubcategoriaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CatalogoAdminServiceImpl implements CatalogoAdminService {

    private final ProductoService productoService;
    private final ProductoRepository productoRepository;
    private final SkuService skuService;
    private final PropiedadProductoService propiedadProductoService;
    private final SubcategoriaService subcategoriaService;
    private final SubcategoriaRepository subcategoriaRepository;

    @Transactional
    @Override
    public Producto crearProducto(Producto producto) {
        return this.productoService.crearProducto(producto);
    }

    @Transactional
    @Override
    public Sku crearSku(Long productoId, Sku sku) {
        Producto producto = this.productoService.obtenerProducto(productoId);

        return this.skuService.crearNuevoSku(sku, producto);
    }

    @Transactional
    @Override
    public void eliminarSku(Long skuId) {
        this.skuService.eliminarSku(skuId);
    }

    @Transactional
    @Override
    public void eliminarSkusProducto(Long productoId) {
        Producto producto = this.productoService.obtenerProducto(productoId);

        producto.getSkus().clear();

        this.productoRepository.save(producto);
    }

    @Transactional
    @Override
    public PropiedadProducto crearPropiedadProducto(PropiedadProducto propiedad) {
        return this.propiedadProductoService.crearPropiedadProducto(propiedad);
    }

    @Transactional
    @Override
    public PropiedadProducto crearPropiedadProducto(Long productoId, PropiedadProducto propiedad) {
        Producto producto = this.productoService.obtenerProducto(productoId);
        PropiedadProducto propiedadNueva = this.propiedadProductoService.crearPropiedadProducto(propiedad);

        producto.getPropiedades().add(propiedadNueva);
        producto.getSubcategoria().getPropiedades().add(propiedadNueva);
        this.productoRepository.save(producto);

        return propiedadNueva;
    }

    @Transactional
    @Override
    public PropiedadProducto crearPropiedadProductoSubcategoria(Long subcategoriaId, PropiedadProducto propiedad) {
        Subcategoria subcategoria = this.subcategoriaService.obtenerSubcategoria(subcategoriaId);

        PropiedadProducto propiedadNueva = this.propiedadProductoService.crearPropiedadProducto(propiedad);

        subcategoria.getPropiedades().add(propiedadNueva);
        this.subcategoriaRepository.save(subcategoria);
        return propiedadNueva;
    }

    @Transactional
    @Override
    public PropiedadProducto crearValorPropiedad(Long propiedadId, ValorPropiedadProducto valor) {
        return this.propiedadProductoService.crearValorPropiedad(propiedadId, valor);
    }

    @Transactional
    @Override
    public Map<String, Object> generarSkusProducto(Long productoId) {
        Producto producto = this.productoService.obtenerProducto(productoId);
        return this.skuService.generarSkusProducto(producto);
    }

    @Transactional
    @Override
    public void asignarPropiedadAProducto(Long productoId, Long propiedadId) {
        Producto producto = this.productoService.obtenerProducto(productoId);
        PropiedadProducto propiedad = this.propiedadProductoService.obtenerPropiedadProducto(propiedadId);

        if (this.validarPropiedad(producto.getSubcategoria(), propiedad)) throw new ProductoException("La propiedad: ".concat(propiedad.getNombre()).concat(" " +
                "no pertenece a la subcategoría: ".concat(producto.getSubcategoria().getNombre())));

        producto.getPropiedades().add(propiedad);
        this.productoRepository.save(producto);
    }

    @Transactional
    @Override
    public void asignarPropiedadASubcategoria(Long subcategoriaId, Long propiedadId) {
        Subcategoria subcategoria = this.subcategoriaService.obtenerSubcategoria(subcategoriaId);
        PropiedadProducto propiedad = this.propiedadProductoService.obtenerPropiedadProducto(propiedadId);

        if (this.validarPropiedad(subcategoria, propiedad)) throw new ProductoException("La propiedad: ".concat(propiedad.getNombre()).concat(" " +
                "no pertenece a la subcategoría: ".concat(subcategoria.getNombre())));

        subcategoria.getPropiedades().add(propiedad);
        this.subcategoriaRepository.save(subcategoria);
    }

    private boolean validarPropiedad(Subcategoria subcategoria, PropiedadProducto propiedad) {
        return !subcategoria.getPropiedades().contains(propiedad);
    }
}

package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.*;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import com.deofis.tiendaapirest.productos.repositories.SubcategoriaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    @Override
    public Imagen subirFotoPpalProducto(Long productoId, MultipartFile foto) {
        return null;
    }

    @Override
    public byte[] obtenerFotoPpalProducto(Long productoId) {
        return new byte[0];
    }

    @Override
    public void eliminarFotoProducto(Long productoId) {

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

        if (!this.validarPropiedad(producto, propiedad)) throw new ProductoException("La propiedad: "
                .concat(propiedad.getNombre()).concat(" ya está asignada al producto: " )
                .concat(producto.getNombre()));

        producto.getPropiedades().add(propiedad);
        this.productoRepository.save(producto);
    }

    @Transactional
    @Override
    public void asignarPropiedadASubcategoria(Long subcategoriaId, Long propiedadId) {
        Subcategoria subcategoria = this.subcategoriaService.obtenerSubcategoria(subcategoriaId);
        PropiedadProducto propiedad = this.propiedadProductoService.obtenerPropiedadProducto(propiedadId);

        if (!this.validarPropiedad(subcategoria, propiedad)) throw new ProductoException("La propiedad: "
                .concat(propiedad.getNombre()).concat(" ya está asignada a la subcategoría: "
                + subcategoria.getNombre()));

        subcategoria.getPropiedades().add(propiedad);
        this.subcategoriaRepository.save(subcategoria);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PropiedadProducto> obtenerPropiedadesProducto() {
        return this.propiedadProductoService.obtenerPropiedadesProducto();
    }

    @Transactional(readOnly = true)
    @Override
    public PropiedadProducto obtenerPropiedadProducto(Long propiedadId) {
        return this.propiedadProductoService.obtenerPropiedadProducto(propiedadId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ValorPropiedadProducto> obtenerValoresDePropiedad(Long propiedadId) {
        return this.propiedadProductoService.obtenerValoresDePropiedad(propiedadId);
    }

    private boolean validarPropiedad(Subcategoria subcategoria, PropiedadProducto propiedad) {
        return !subcategoria.getPropiedades().contains(propiedad);
    }

    private boolean validarPropiedad(Producto producto, PropiedadProducto propiedad) {
        return !producto.getPropiedades().contains(propiedad);
    }
}

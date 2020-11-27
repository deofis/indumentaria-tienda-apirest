package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.MarcaRepository;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import com.deofis.tiendaapirest.productos.repositories.SubcategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CatalogoServiceImpl implements CatalogoService {

    private final ProductoRepository productoRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final MarcaRepository marcaRepository;
    private final BuscadorProductosService buscadorProductosService;

    @Transactional(readOnly = true)
    @Override
    public List<Producto> buscarProductos(String termino) {
        return this.buscadorProductosService.buscarProductos(termino);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Marca> listarMarcas() {
        return this.marcaRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> obtenerProductosDestacados() {
        return this.productoRepository
                .findAllByDestacadoIsTrueAndActivoIsTrue();
    }

    @Transactional(readOnly = true)
    @Override
    public Producto obtenerProducto(Long id) {
        return this.productoRepository.findById(id)
                .orElseThrow(() -> new ProductoException("No existe el producto con id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> productosPrecioMenorMayor() {
        return this.productoRepository.findAllByOrderByPrecioAsc();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> productosPrecioMayorMenor() {
        return this.productoRepository.findAllByOrderByPrecioDesc();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> productosPorSubcategoria(Long subcategoriaId) {
        Subcategoria subcategoria = this.subcategoriaRepository.findById(subcategoriaId)
                .orElseThrow(() -> new ProductoException("No se encontró la categoria con id: " + subcategoriaId));

        return this.productoRepository.findAllBySubcategoriaAndActivoIsTrue(subcategoria);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> productosPorMarca(Long marcaId) {
        Marca marca = this.marcaRepository.findById(marcaId)
                .orElseThrow(() -> new ProductoException("No se encontró la marca con id: " + marcaId));

        return this.productoRepository.findAllByMarcaAndActivoIsTrue(marca);
    }

    @Override
    public List<Producto> productosPorPrecio(Double precioMax) {
        return this.productoRepository.findAllByPrecioBetween(0.00, precioMax);
    }
}

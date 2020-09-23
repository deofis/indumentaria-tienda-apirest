package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Categoria;
import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.CategoriaRepository;
import com.deofis.tiendaapirest.productos.repositories.MarcaRepository;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CatalogoServiceImpl implements CatalogoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;

    @Override
    public List<Categoria> listarCategorias() {
        return this.categoriaRepository.findAll();
    }

    @Override
    public List<Marca> listarMarcas() {
        return this.marcaRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> buscarProductos(String termino) {
        return this.productoRepository
                .findAllByNombreContainingIgnoringCaseAndActivoIsTrueOrderByNombreAsc(termino);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> obtenerProductosDestacados() {
        return this.productoRepository
                .findAllByDestacadoIsTrueAndActivoIsTrue();
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
    public List<Producto> productosPorCategoria(Long categoriaId) {
        Categoria categoria = this.categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ProductoException("No se encontró la categoria con id: " + categoriaId));

        return this.productoRepository.findAllByCategoriaAndActivoIsTrue(categoria);
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

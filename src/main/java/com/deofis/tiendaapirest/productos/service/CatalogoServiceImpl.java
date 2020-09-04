package com.deofis.tiendaapirest.productos.service;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CatalogoServiceImpl implements CatalogoService {

    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> obtenerProductosFavoritos() {
        return null;
    }

    @Override
    public List<Producto> buscarProductos(String termino) {
        return this.productoRepository.findAllByNombreContainingIgnoringCaseAndActivoIsTrueOrderByNombreAsc(termino);
    }

    @Override
    public List<Producto> obtenerProductosDestacados() {
        return this.productoRepository.findAllByDestacadoIsTrueAndActivoIsTrue();
    }

    @Override
    public List<Producto> productosPrecioMenorMayor() {
        return null;
    }

    @Override
    public List<Producto> productosPrecioMayorMenor() {
        return null;
    }

    @Override
    public List<Producto> productosPorCategoria() {
        return null;
    }

    @Override
    public List<Producto> productosPorMarca() {
        return null;
    }
}

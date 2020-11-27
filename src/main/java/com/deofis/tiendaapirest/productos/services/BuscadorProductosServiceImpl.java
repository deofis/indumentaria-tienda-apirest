package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.repositories.MarcaRepository;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import com.deofis.tiendaapirest.productos.repositories.SubcategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BuscadorProductosServiceImpl implements BuscadorProductosService {

    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;
    private final SubcategoriaRepository subcategoriaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Producto> buscarProductos(String termino) {
        List<Producto> productosTotal = new ArrayList<>();

        List<Producto> productosEncontradosPorNombre = this.encontrarProductosPorNombre(termino);
        List<Producto> productosEncontradosPorMarca = this.encontrarProductosPorMarca(termino);
        List<Producto> productosEncontradosPorSubcategoria = this.encontrarProductosPorSubcategoria(termino);

        if (productosEncontradosPorNombre.size() != 0) productosTotal.addAll(productosEncontradosPorNombre);
        if (productosEncontradosPorMarca.size() != 0) productosTotal.addAll(productosEncontradosPorMarca);
        if (productosEncontradosPorSubcategoria.size() != 0) productosTotal.addAll(productosEncontradosPorSubcategoria);



        return this.eliminarDuplicados(productosTotal);
    }

    private List<Producto> encontrarProductosPorNombre(String termino) {
        return this.productoRepository
                .findAllByNombreContainingIgnoringCaseAndActivoIsTrueOrderByNombreAsc(termino);
    }

    private List<Producto> encontrarProductosPorMarca(String termino) {
        List<Producto> productos = new ArrayList<>();
        this.marcaRepository.findByNombreContainingIgnoringCase(termino).ifPresent(marca -> productos
                .addAll(this.productoRepository.findAllByMarcaAndActivoIsTrue(marca)));

        return productos;
    }

    private List<Producto> encontrarProductosPorSubcategoria(String termino) {
        List<Producto> productos = new ArrayList<>();
        this.subcategoriaRepository.findByNombreContainingIgnoringCase(termino).ifPresent(subcategoria -> productos
                .addAll(this.productoRepository.findAllBySubcategoriaAndActivoIsTrue(subcategoria)));

        return productos;
    }

    private <T> List<T> eliminarDuplicados(List<T> lista) {
        ArrayList<T> nuevaLista = new ArrayList<>();

        for (T elemento: lista) {
            if (!nuevaLista.contains(elemento))
                nuevaLista.add(elemento);
        }

        return nuevaLista;
    }
}

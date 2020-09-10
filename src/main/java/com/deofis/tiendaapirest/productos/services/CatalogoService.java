package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;

import java.util.List;

/**
 * Este servicio tiene la lógica relacionada con el catálogo de productos para mostrar,
 * tales como, obtener los productos por categoria, por marca, etc.
 */
public interface CatalogoService {

    // Método para desarrollo previo a usuarios
    List<Producto> obtenerProductosFavoritos();

    List<Producto> buscarProductos(String termino);

    List<Producto> obtenerProductosDestacados();

    List<Producto> productosPrecioMenorMayor();

    List<Producto> productosPrecioMayorMenor();

    List<Producto> productosPorCategoria();

    List<Producto> productosPorMarca();
}

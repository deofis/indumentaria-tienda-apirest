package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;

import java.util.List;

/**
 * Servicio que se encarga de buscar productos por un término requerido.
 */

public interface BuscadorProductosService {

    /**
     * Busca productos por término. Este término debe coincidir con nombres de productos, marcas o
     * subcategorias del mismo y el listado de productos debe contener productos sin repetirse.
     * @param termino String termino a buscar. Puede contener: nombre, marca o subcategoría de producto.
     * @return List listado de productos encontrados.
     */
    List<Producto> buscarProductos(String termino);
}

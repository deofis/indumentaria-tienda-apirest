package com.deofis.tiendaapirest.productos.service;

import com.deofis.tiendaapirest.productos.domain.Producto;

import java.util.List;

public interface ProductoService {

    Producto registrarProducto(Producto producto);

    List<Producto> obtenerProductos();
}

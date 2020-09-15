package com.deofis.tiendaapirest.perfiles.services;

import com.deofis.tiendaapirest.perfiles.domain.Carrito;

public interface CarritoService {

    /**
     * Agrega un producto al carrito (cantidad por defecto: 1)
     * @param productoId Long id del producto a agregar.
     * @return Carrito actualizado.
     */
    Carrito agregarProducto(Long productoId);

    /**
     * Actualizar la cantidad de productos de un carrito.
     * @param productoId Long id del producto a actualizar cantidad.
     * @param cantidad Integer nueva cantidad de producto.
     * @return Carrito actualizado.
     */
    Carrito actualizarCantidad(Long productoId, Integer cantidad);

    /**
     * Quita un producto del carrito.
     * @param productoId Long id del producto a quitar del carrito.
     * @return Carrito actualizado.
     */
    Carrito quitarProducto(Long productoId);

    void vaciar();
}

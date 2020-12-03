package com.deofis.tiendaapirest.perfiles.services;

import com.deofis.tiendaapirest.perfiles.domain.Carrito;

public interface CarritoService {

    /**
     * Agrega un Sku de Producto al carrito (cantidad por defecto: 1)
     * @param skuId Long id del sku a agregar.
     * @return Carrito actualizado.
     */
    Carrito agregarItem(Long skuId);

    /**
     * Actualizar la cantidad de skus de un carrito.
     * @param skuId Long id del sku a actualizar cantidad.
     * @param cantidad Integer nueva cantidad de sku.
     * @return Carrito actualizado.
     */
    Carrito actualizarCantidad(Long skuId, Integer cantidad);

    /**
     * Quita un Sku de Producto del carrito.
     * @param skuId Long id del sku a quitar del carrito.
     * @return Carrito actualizado.
     */
    Carrito quitarItem(Long skuId);

    /**
     * Vacía el carrito de compras, dejándolo sin items.
     */
    void vaciar();
}

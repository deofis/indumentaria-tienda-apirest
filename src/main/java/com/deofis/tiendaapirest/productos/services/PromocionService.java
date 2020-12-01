package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.Promocion;
import com.deofis.tiendaapirest.productos.domain.Sku;

/**
 * Servicio que se encarga de crear y obtener las {@link Promocion}es (ofertas) asociadas a
 * {@link Producto}s o a {@link Sku}s. Los Productos y Skus pueden tener asociada solo una
 * promoción al mismo tiempo, con los datos de vigencia y precios de oferta.
 */

public interface PromocionService {

    /**
     * Programa una nueva oferta para un {@link Producto}, asociandole la nueva {@link Promocion} a crear,
     * con el precio de oferta y fecha de vigencia requerida.
     * @param productoId Long id del producto a programar la promoción.
     * @param promocion Promocion con los datos:  precio que tendrá prodoucto durante la
     *                     vigencia de la promoción, fecha hasta que será vigente la misma.
     * @return Producto actualizado con la promoción creada.
     */
    Producto programarOfertaProducto(Long productoId, Promocion promocion);

    /**
     * Programa una nueva oferta para un {@link Sku}, asociandole la nueva {@link Promocion} a crear,
     * con el precio de oferta y fecha de vigencia requerida.
     * @param skuId Long id del sku a programar la promoción.
     * @param promocion Promocion con los datos:  precio que tendrá el sku durante la
     *                     vigencia de la promoción, fecha hasta que será vigente la misma.
     * @return Sku actualizado con la promoción creada.
     */
    Sku programarOfertaSku(Long skuId, Promocion promocion);

    /**
     * Programa una {@link Promocion} para todos los {@link Producto}s, y sus {@link Sku},
     * que pertenecen a la {@link com.deofis.tiendaapirest.productos.domain.Subcategoria}
     * requerida.
     * @param subcategoriaId Long id de la subcategoría a crear promociones.
     * @param promocion Promocion con los datos para crear la promoción: porcentaje a aplicar en
     *                  las promociones, y las fechas (desde, hasta) de vigencia.
     * @return Integer cantidad de productos promocionados.
     */
    Integer programarOfertaProductosSubcategoria(Long subcategoriaId, Promocion promocion);

    /**
     * Obtiene la {@link Promocion} de un {@link Producto} requerido.
     * @param productoId Long id del producto.
     * @return Promocion.
     */
    Promocion obtenerPromocionProducto(Long productoId);

    /**
     * Obtiene la {@link Promocion} de un {@link Sku} requerido.
     * @param skuId Long id del sku.
     * @return Promocion
     */
    Promocion obtenerPromocionSku(Long skuId);
}

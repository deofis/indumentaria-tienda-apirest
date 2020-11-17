package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.Sku;
import com.deofis.tiendaapirest.productos.domain.ValorPropiedadProducto;

import java.util.Map;

/**
 * Este servicio se encarga del catalogo de administración de Productos. Tiene las responsabilidades
 * en cuanto a Producto se refiere, tales como, la creación, obtencion, actualizacion, etc.. de productos,
 * subcategorias, skus, propiedades de producto y valores.
 *
 * Es el servicio que une toda la logica de negocio acerca de los productos y lo que deriva de los mismos.
 */

public interface CatalogoAdminService {

    /**
     * Crea un nuevo {@link Producto}.
     * @param producto Producto nuevo.
     * @return Producto creado y guardado.
     */
    Producto crearProducto(Producto producto);

    /**
     * Crea un nuevo {@link Sku} de un {@link Producto}.
     * @param productoId Long id del producto al que se requiere crear el Sku.
     * @param sku Sku nuevo.
     * @return Sku creado y guardado.
     */
    Sku crearSku(Long productoId, Sku sku);

    /**
     * Crea una nueva {@link PropiedadProducto}.
     * @param propiedad PropiedadProducto nuevo.
     * @return PropiedadProducto creada y guardada.
     */
    PropiedadProducto crearPropiedadProducto(PropiedadProducto propiedad);

    /**
     * Crea una nueva {@link PropiedadProducto} para un {@link Producto}. Asigna automáticamente
     * la propiedad al producto requerido.
     * @param productoId Long id del producto a crear la nueva propiedad.
     * @param propiedad PropiedadProducto nueva.
     * @return PropiedadProducto creada, guardada y asignada al producto correspondiente.
     */
    PropiedadProducto crearPropiedadProducto(Long productoId, PropiedadProducto propiedad);

    /**
     * Crea un nuevo {@link ValorPropiedadProducto} que pertenecerá a una {@link PropiedadProducto} requerida.
     * @param propiedadId Long id de la propiedad a la cual se asignará el nuevo valor.
     * @param valor ValorPropiedadProducto nuevo.
     * @return ValorPropiedadProducto creado, guardado y asignado a la propiedad correspondiente.
     */
    PropiedadProducto crearValorPropiedad(Long propiedadId, ValorPropiedadProducto valor);

    /**
     * Genera un listado de {@link Sku} a partir de un {@link Producto} requerido. Por cada propiedad
     * y cada valor de dicha propiedad del producto requerido, se generarán las distintas combinaciones
     * posibles, y asignarán a un SKU individual, lo que será finalmente un producto vendible con sus
     * propiedades seleccionadas.
     * @param productoId Long id del producto a generar sus SKUs.
     * @return Map que contiene la cantidad y combinaciones generadas.
     */
    Map<String, Object> generarSkusProducto(Long productoId);
}

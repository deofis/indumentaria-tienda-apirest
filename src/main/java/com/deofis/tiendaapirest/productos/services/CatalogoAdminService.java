package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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
     * Crea, sube y vincula la foto principal a un {@link Producto}.
     * @param productoId Long id del producto a subir su foto.
     * @param foto Multipartfile archivo con la imagen a subir.
     * @return {@link Imagen} con los datos del archivo subido.
     */
    Imagen subirFotoPpalProducto(Long productoId, MultipartFile foto);

    /**
     * Obtiene la foto principal de un {@link Producto}.
     * @param productoId Long id del producto.
     * @return imagen descargada en bytes.
     */
    byte[] obtenerFotoPpalProducto(Long productoId);

    /**
     * Obtiene el path de foto de un {@link Producto}.
     * @param productoId Long id del producto.
     * @return String path de foto del producto.
     */
    String obtenerPathFotoPpalProducto(Long productoId);

    /**
     * Elimina una foto asociada a un {@link Producto}.
     * @param productoId Long id del producto.
     */
    void eliminarFotoPpalProducto(Long productoId);

    /**
     * Crea un nuevo {@link Sku} de un {@link Producto}.
     * @param productoId Long id del producto al que se requiere crear el Sku.
     * @param sku Sku nuevo.
     * @return Sku creado y guardado.
     */
    Sku crearSku(Long productoId, Sku sku);

    /**
     * Elimina un {@link Sku} requerido.
     * @param skuId Long id del SKU a eliminar.
     */
    void eliminarSku(Long skuId);

    /**
     * Elimina todos los SKUs {@link Sku} de un producto requerido.
     * @param productoId Long id del producto a eliminar sus Skus.
     */
    void eliminarSkusProducto(Long productoId);

    /**
     * Crea una nueva {@link PropiedadProducto}.
     * @param propiedad PropiedadProducto nuevo.
     * @return PropiedadProducto creada y guardada.
     */
    PropiedadProducto crearPropiedadProducto(PropiedadProducto propiedad);

    /**
     * Crea una nueva {@link PropiedadProducto} para un {@link Producto}. Asigna automáticamente
     * la propiedad al producto requerido, y, si es necesario, a la subcategoría del mismo.
     * @param productoId Long id del producto a crear la nueva propiedad.
     * @param propiedad PropiedadProducto nueva.
     * @return PropiedadProducto creada, guardada y asignada al producto correspondiente.
     */
    PropiedadProducto crearPropiedadProducto(Long productoId, PropiedadProducto propiedad);

    /**
     * Crea una nueva {@link PropiedadProducto} para una {@link com.deofis.tiendaapirest.productos.domain.Subcategoria}.
     * Asigna automáticamente la propiedad a la subcategoría requerida.
     * @param subcategoriaId Long id de la subcategoría a crear la nueva propiedad.
     * @param propiedad PropiedadProducto nueva.
     * @return PropiedadProducto creada, guardada y asignada a la subcategoría correspondiente.
     */
    PropiedadProducto crearPropiedadProductoSubcategoria(Long subcategoriaId, PropiedadProducto propiedad);

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

    /**
     * Asigna una {@link PropiedadProducto} existente, a un {@link Producto} existente.
     * @param productoId Long id del producto.
     * @param propiedadId Long id de la propiedad a asignar.
     */
    void asignarPropiedadAProducto(Long productoId, Long propiedadId);

    /**
     * Asigna una {@link PropiedadProducto} existente, a una {@link com.deofis.tiendaapirest.productos.domain.Subcategoria}
     * @param subcategoriaId Long id de la subcategoría.
     * @param propiedadId Long id de la propiedad a asignar.
     */
    void asignarPropiedadASubcategoria(Long subcategoriaId, Long propiedadId);

    /**
     * Obtiene un listado con todas las {@link PropiedadProducto}.
     * @return List propiedades.
     */
    List<PropiedadProducto> obtenerPropiedadesProducto();

    /**
     * Obtiene una {@link PropiedadProducto} requerida a través de su id.
     * @param propiedadId Long id de la propiedad.
     * @return PropiedadProducto
     */
    PropiedadProducto obtenerPropiedadProducto(Long propiedadId);

    /**
     * Obtiene los {@link ValorPropiedadProducto} de una {@link PropiedadProducto}
     * @param propiedadId Long id de la propiedad a obtener sus valores.
     * @return List listado de los valores de la propiedad.
     */
    List<ValorPropiedadProducto> obtenerValoresDePropiedad(Long propiedadId);
}

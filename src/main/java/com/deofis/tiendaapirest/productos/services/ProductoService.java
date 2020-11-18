package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.Sku;
import com.deofis.tiendaapirest.productos.domain.UnidadMedida;

import java.util.List;

/**
 * Servicio que se encarga de los {@link Producto}. Maneja la creación, actualización y obtención de productos,
 * asi como la activación/desactivación de los mismos. También es responsable de destacar o quitar de
 * destacados a un producto requerido.
 */

public interface ProductoService {

    /**
     * Método que se encarga de registrar un nuevo producto en la BD y devolverlo. También
     * se encarga de generar el SKU por defecto.
     * @param producto Recibe un producto con datos validos desde el controlador.
     * @return Producto guardado en la Base de Datos.
     */
    Producto crearProducto(Producto producto);

    /**
     * Método que lista todos los productos registrados.
     * @return List de Productos.
     */
    List<Producto> obtenerProductos();

    /**
     * Método que obtiene un producto guardado a través del Id.
     * @param id Long con el valor del id del producto.
     * @return Producto en la BD.
     */
    Producto obtenerProducto(Long id);

    /**
     * Método que modifica algún dato del producto, lo guarda y devuelve actualizado.
     * @param producto Producto ya modificado.
     * @param id Long del id del producto a modificar.
     * @return Producto actualizado.
     */
    Producto actualizarProducto(Producto producto, Long id);

    /**
     * Método para registrar la baja lógica de un producto.
     * @param id Long id del producto a dar de baja.
     */
    void darDeBaja(Producto producto, Long id);

    /**
     * Método para registrar el alta de un producto.
     * @param id Long id del producto a activar.
     */
    void darDeAlta(Producto producto, Long id);

    /**
     * Método para destacar/quitar de destacados a un producto.
     * @param producto Producto a destacar/quitar destacados.
     * @param id Long del id del producto.
     */
    void destacar(Producto producto, Long id);

    /**
     * Obtiene un listado con todas las unidades de medida disponibles.
     * @return List unidades medida.
     */
    List<UnidadMedida> obtenerUnidadesMedida();

    /**
     * Obtiene un listado de todas las propiedades asociadas a un producto.
     * @param productoId Long id del producto requerido a listar sus propiedades.
     * @return List de las propiedades del producto requerido.
     */
    List<PropiedadProducto> obtenerPropiedadesDeProducto(Long productoId);

    /**
     * Obtiene un listado con todos los SKUs de un producto requerido.
     * @param productoId Long id del producto a listar sus SKUs.
     * @return List de SKUs.
     */
    List<Sku> obtenerSkusProducto(Long productoId);
}

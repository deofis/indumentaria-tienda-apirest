package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.UnidadMedida;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductoService {

    /**
     * Método que se encarga de registrar un nuevo producto en la BD y devolverlo.
     * @param producto Recibe un producto con datos validos desde el controlador.
     * @return Producto guardado en la Base de Datos.
     */
    Producto crear(Producto producto);

    /**
     * Método que lista todos los productos en la Base de Datos ordenados por nombre asc.
     * @return List de Productos ordenados por nombre.
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
    Producto actualizar(Producto producto, Long id);

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
     * Método con fines de prueba. Al avanzar en desarrollo.
     * @param producto
     * @param id
     */
    void marcarFavorito(Producto producto, Long id);

    /**
     * Obtiene un listado con todas las unidades de medida disponibles.
     * @return List unidades medida.
     */
    List<UnidadMedida> obtenerUnidadesMedida();

    List<Producto> importarDeCSV(MultipartFile archivo);
}

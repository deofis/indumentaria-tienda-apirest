package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Categoria;
import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.domain.Producto;

import java.util.List;

/**
 * Este servicio tiene la lógica relacionada con el catálogo de productos para mostrar,
 * tales como, obtener los productos por categoria, por marca, etc.
 */
public interface CatalogoService {

    /**
     * Obtiene un listado desordenado de todas las categorias.
     * @return List de categorias.
     */
    List<Categoria> listarCategorias();

    /**
     * Obtiene un listado desordenado de todas las marcas.
     * @return List de marcas.
     */
    List<Marca> listarMarcas();

    /**
     * Busca y devuelve un listado de productos que contienen al termino que se envía.
     * @param termino String consulta sobre los productos a buscar.
     * @return List listado de productos que coinciden con el término de búsqueda.
     */
    List<Producto> buscarProductos(String termino);

    /**
     * Obtiene el listado de los productos destacados, que al ser destacados, tambiíen tienen que
     * estar activos.
     * @return List listado de productos activos y destacados.
     */
    List<Producto> obtenerProductosDestacados();

    /**
     * Obtiene un producto por id.
     * @param id Long id del producto.
     * @return Producto obtenido.
     */
    Producto obtenerProducto(Long id);

    /**
     * Obtiene los producots ordenados por precio de menor a mayor.
     * @return List productos ordenados por precio
     */
    List<Producto> productosPrecioMenorMayor();

    /**
     * Obtiene los producots ordenados por precio de mayor a menor.
     * @return List productos ordenados por precio
     */
    List<Producto> productosPrecioMayorMenor();

    /**
     * Obtiene un listado de los productos que pertenecen a una categoría.
     * @param categoriaId Long id de la categoría.
     * @return List listado de productos filtrados por categoria.
     */
    List<Producto> productosPorCategoria(Long categoriaId);

    /**
     * Obtiene un listado de los productos de una marca.
     * @param marcaId Long id de la marca.
     * @return List de los productos filtrados por marca.
     */
    List<Producto> productosPorMarca(Long marcaId);

    /**
     * Obtiene los productos que van de $0 a un máximo precio.
     * @param precioMax Double precio máximo a filtrar.
     * @return List productos.
     */
    List<Producto> productosPorPrecio(Double precioMax);
}

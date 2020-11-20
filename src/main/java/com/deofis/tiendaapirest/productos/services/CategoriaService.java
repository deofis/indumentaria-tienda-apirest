package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Categoria;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;

import java.util.List;

public interface CategoriaService {

    /**
     * Método para registrar una nueva categoría. SERVICIO solo utilizado
     * por desarrolladores, no debería llamarse en ningun controlador.
     * @param categoria Categoria a guardar.
     * @return Categoria guardada en la BD.
     */
    Categoria crear(Categoria categoria);

    /**
     * Método para actualizar una categoría. SERVICIO solo utilizado
     * por desarrolladores, no debería llamarse en ningun controlador.
     * @param categoria Categoria actualizada.
     * @param id Long id de la categoría a actualizar.
     * @return Categoria actualizada.
     */
    Categoria actualizar(Categoria categoria, Long id);

    /**
     * Método para obtener todas las categorias ordenadas por nombre asc.
     * @return List con las categorias en la BD.
     */
    List<Categoria> obtenerCategorias();

    /**
     * Método para obtener una categoría.
     * @param id Long id de la categoría a obtener de la BD.
     * @return Categoria.
     */
    Categoria obtenerCategoria(Long id);

    /**
     * Obtiene un listado con las subcategorias de una categoria.
     * @param categoriaId Long id de la categoria a listar sus subcategorias.
     * @return List listado de subcategorias pertenecientes a la categoria requerida.
     */
    List<Subcategoria> obtenerSubcategorias(Long categoriaId);

    /**
     * Obtiene una {@link Subcategoria} de una {@link Categoria} requerida.
     * @param categoriaId Long id de la categoría.
     * @param subcategoriaId Long id de la subcategoría.
     * @return Subcategoria.
     */
    Subcategoria obtenerSubcategoriaDeCategoria(Long categoriaId, Long subcategoriaId);
}

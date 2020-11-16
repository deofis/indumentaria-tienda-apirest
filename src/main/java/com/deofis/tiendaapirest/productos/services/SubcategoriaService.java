package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;
import com.deofis.tiendaapirest.productos.domain.ValorPropiedadProducto;

import java.util.List;

public interface SubcategoriaService {
    /**
     * Obtiene la lista con todas las subcategorias.
     * @return List con subcategorias.
     */
    List<Subcategoria> listarSubcategorias();

    /**
     * Obtiene una subcategoria a través de su id.
     * @param subcategoriaId Long id de la subcategoria.
     * @return Subcategoria requerida.
     */
    Subcategoria obtenerSubcategoria(Long subcategoriaId);

    /**
     * Agrega una nueva propiedad a una subcategoria existente.
     * @param subcategoriaId Long id de la subcategoria a la cual se requiere agregar la propiedad.
     * @param propiedadProducto Propiedad nueva a agregar.
     * @return Subcategoria actualizada con la nueva propiedad.
     */
    Subcategoria agregarPropiedad(Long subcategoriaId, PropiedadProducto propiedadProducto);

    /**
     * Obtiene una propiedad a través de su id, que pertenezca a la lista de propiedades de una subcategoria específica.
     * @param subcategoriaId Long id de la subcategoria a la que pertenece la propiedad requerida.
     * @param propiedadId Long id de la propiedad requerida.
     * @return Propiedad.
     */
    PropiedadProducto obtenerPropiedad(Long subcategoriaId, Long propiedadId);

    /**
     * Obtiene la lista con todas las propiedades que pertenecen a una subcategoria requerida.
     * @param subcategoriaId Long id de la subcategoria.
     * @return List de las propiedades de la subcategoria.
     */
    List<PropiedadProducto> obtenerPropiedadesSubcategoria(Long subcategoriaId);

    /**
     * Agrega un nuevo valor a una propiedad solicitada, que pertenece a la subcategoria requerida.
     * @param subcategoriaId Long id de la subcategoria a la que pertenece la propiedad.
     * @param propiedadId Long id de la propiedad a la cual se requiere agregar el nuevo valor.
     * @param valorPropiedadProducto ValorPropiedad nueva a agregar.
     * @return Propiedad actualizada con el nuevo valor agregado.
     */
    PropiedadProducto agregarValor(Long subcategoriaId, Long propiedadId, ValorPropiedadProducto valorPropiedadProducto);

    /**
     * Obtiene una lista con todos los valores de la propiedad solicitada, que pertenece a una subcategoria indicada.
     * @param subcategoriaId Long id de la subcategoria a la que pertenece la propiedad requerida.
     * @param propiedadId Long id de la propiedad la cual se desea listar sus valores.
     * @return List con los valores de la propiedad requerida.
     */
    List<ValorPropiedadProducto> obtenerValoresPropiedad(Long subcategoriaId, Long propiedadId);
}

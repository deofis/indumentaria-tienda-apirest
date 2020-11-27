package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Imagen;
import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubcategoriaService {

    /**
     * Obtiene la lista con todas las subcategorias.
     * @return List con subcategorias.
     */
    List<Subcategoria> obtenerSubcategorias();

    /**
     * Obtiene una subcategoria a través de su id.
     * @param subcategoriaId Long id de la subcategoria.
     * @return Subcategoria requerida.
     */
    Subcategoria obtenerSubcategoria(Long subcategoriaId);

    /**
     * Obtiene una propiedad a través de su id, que pertenezca a la lista de propiedades de una subcategoria
     * específica.
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
     * Crea, sube y vincula foto de {@link Subcategoria}.
     * @param subcategoriaId Long id de la subcategoría.
     * @param foto MultipartFile archivo que contiene la foto a subir.
     * @return {@link Imagen} con los datos del archivo subido.
     */
    Imagen subirFotoSubcategoria(Long subcategoriaId, MultipartFile foto);

    /**
     * Obtiene la foto de una {@link Subcategoria}.
     * @param subcategoriaId Long id de la categoría.
     * @return imagen descargada en bytes.
     */
    byte[] obtenerFotoSubcategoria(Long subcategoriaId);

    /**
     * Obtiene el path de foto de una {@link Subcategoria}.
     * @param subcategoriaId Long id de la categoría.
     * @return String path de foto de la categoría.
     */
    String obtenerPathFotoSubcategoria(Long subcategoriaId);

    /**
     * Elimina la foto asociada a una {@link Subcategoria}.
     * @param subcategoriaId Long id de la categoría.
     */
    void eliminarFotoSubcategoria(Long subcategoriaId);

    // todo: Catalogo --> obtener productos de subcategoría: lista todos los productos de una subcategoría.
}

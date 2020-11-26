package com.deofis.tiendaapirest.productos.services.images;

import com.deofis.tiendaapirest.productos.domain.Imagen;
import org.springframework.web.multipart.MultipartFile;

/**
 * Este servicio se encarga de subir las imágenes utilizadas por Objetos que
 * los necesiten ({@link com.deofis.tiendaapirest.productos.domain.Producto}
 * y {@link com.deofis.tiendaapirest.productos.domain.Sku}).
 */
public interface ImageService {

    /**
     * Método que se encarga de subir la imágen.
     * @param archivo MultipartFile archivo a subir.
     * @return {@link Imagen} con los datos cargados de la subida.
     */
    Imagen subirImagen(MultipartFile archivo);

    /**
     * Devuelve bytes asociados a una imágen guardada.
     * @param imagen Imagen que contiene el path del archivo imagen a descargar.
     * @return byte[] bytes del archivo imagen.
     */
    byte[] descargarImagen(Imagen imagen);

    /**
     * Elimina un archivo de imagen a través de su path.
     * @param imagen Imagen que contiene el path del archivo imagen a eliminar.
     */
    void eliminarImagen(Imagen imagen);
}

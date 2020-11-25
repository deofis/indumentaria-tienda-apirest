package com.deofis.tiendaapirest.productos.services.images;

import com.deofis.tiendaapirest.productos.domain.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
     * @param path String path del archivo imagen a descargar.
     * @return byte[] bytes del archivo imagen.
     * @throws IOException por los archivos.
     */
    byte[] descargarImagen(String path) throws IOException;

    /**
     * Elimina un archivo de imagen a través de su path.
     * @param path String path del archivo imagen a eliminar.
     */
    void eliminarImagen(String path);
}

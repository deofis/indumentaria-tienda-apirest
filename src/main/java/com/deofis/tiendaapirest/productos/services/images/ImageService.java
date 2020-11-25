package com.deofis.tiendaapirest.productos.services.images;

import com.deofis.tiendaapirest.productos.domain.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    Imagen subirImagen(MultipartFile archivo);

    byte[] descargarImagen(String path) throws IOException;

    void eliminarImagen(String path);
}

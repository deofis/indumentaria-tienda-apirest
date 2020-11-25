package com.deofis.tiendaapirest.productos.services.images;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileAmazonS3Service {

    String subirArchivoMultipart(MultipartFile multipartFile);
}

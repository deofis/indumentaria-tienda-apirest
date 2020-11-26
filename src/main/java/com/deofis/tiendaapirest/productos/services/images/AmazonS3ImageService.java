package com.deofis.tiendaapirest.productos.services.images;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.deofis.tiendaapirest.productos.domain.Imagen;
import com.deofis.tiendaapirest.productos.dto.AmazonDAO;
import com.deofis.tiendaapirest.productos.exceptions.FileException;
import com.deofis.tiendaapirest.productos.repositories.ImagenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AmazonS3ImageService implements ImageService {

    private final UploadFileAmazonS3Service uploadFileAmazonS3Service;
    private final AmazonDAO amazonDAO;
    private final AmazonS3 amazonS3;
    private final ImagenRepository imagenRepository;

    @Transactional
    @Override
    public Imagen subirImagen(MultipartFile archivo) {

        if (!validarExtensionesImagen(FilenameUtils.getExtension(archivo.getOriginalFilename())))
            throw new FileException("Extension de imagen invalida");

        String path = this.uploadFileAmazonS3Service.subirArchivoMultipart(archivo);

        Imagen imagen = Imagen.builder()
                .imageUrl(this.amazonDAO.getEndpoint().concat(path))
                .path(path).build();

        return this.imagenRepository.save(imagen);
    }

    @Override
    public byte[] descargarImagen(Imagen imagen) {
        byte[] content;

        S3Object s3Object = this.amazonS3.getObject(this.amazonDAO.getBucketName(), imagen.getPath());
        S3ObjectInputStream stream = s3Object.getObjectContent();

        log.info("S3 Object Key --> ".concat(s3Object.getKey()));

        try {
            content = IOUtils.toByteArray(stream);
            s3Object.close();
            log.info("Imagen descargada exitosamente");
        } catch (IOException e) {
            log.warn(e.getMessage());
            throw new FileException("Error al descargar imagen");
        }

        return content;
    }

    @Transactional
    @Override
    public void eliminarImagen(Imagen imagen) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
                this.amazonDAO.getBucketName(),
                imagen.getPath());

        this.amazonS3.deleteObject(deleteObjectRequest);
        this.imagenRepository.deleteById(imagen.getId());
    }

    private boolean validarExtensionesImagen(String extension) {
        List<String> extensionesValidas = Arrays.asList("jpeg", "jpg", "png");
        return extensionesValidas.contains(extension);
    }
}

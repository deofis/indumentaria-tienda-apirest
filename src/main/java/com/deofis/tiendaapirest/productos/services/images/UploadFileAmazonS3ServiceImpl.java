package com.deofis.tiendaapirest.productos.services.images;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.deofis.tiendaapirest.productos.dto.AmazonDAO;
import com.deofis.tiendaapirest.productos.exceptions.FileException;
import com.deofis.tiendaapirest.productos.utils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
public class UploadFileAmazonS3ServiceImpl implements UploadFileAmazonS3Service {

    private final AmazonS3 amazonS3;
    private final AmazonDAO amazonDAO;

    @Override
    public String subirArchivoMultipart(MultipartFile multipartFile) {
        String fileName;

        try {
            File archivo = FileUtils.convertirMultipartAFile(multipartFile);
            fileName = FileUtils.generarFileName(multipartFile);

            this.subirArchivoAS3Bucket(fileName, archivo);
            if (!archivo.delete()) throw new FileException("Error al eliminar el archivo");
        } catch (IOException e) {
            log.warn(e.getMessage().concat(" : ").concat(e.getLocalizedMessage()));
            throw new AmazonS3Exception(e.getMessage());
        }

        log.info("File name --> ".concat(fileName));
        return fileName;
    }

    @Async
    public void subirArchivoAS3Bucket(String fileName, File archivo) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(this.amazonDAO.getBucketName(),
                fileName, archivo)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        this.amazonS3.putObject(putObjectRequest);
    }
}

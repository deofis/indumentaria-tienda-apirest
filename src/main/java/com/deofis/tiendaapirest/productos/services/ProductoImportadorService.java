package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.dto.ProductoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductoImportadorService {

    List<ProductoDTO> recibirCsv(MultipartFile archivo);
}

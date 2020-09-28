package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.dto.ProductoDTO;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductoImportadorServiceImpl implements ProductoImportadorService {

    @Override
    public List<ProductoDTO> recibirCsv(MultipartFile archivo) {
        List<ProductoDTO> productos;

        try(Reader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {
            CsvToBean<ProductoDTO> csvToBean = new CsvToBeanBuilder<ProductoDTO>(reader)
                    .withType(ProductoDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            productos = csvToBean.parse();

            this.setearPropiedades(productos);

            return productos;

        } catch (IOException | RuntimeException e) {
            throw new ProductoException("Error al procesar el archivo CSV. Verifique los datos de su archivo sean correctos.");
        }

    }

    private void setearPropiedades(List<ProductoDTO> dtos) {
        for (ProductoDTO producto: dtos) {
            if (producto.getColor().equals(" ")) {
                producto.setColor(null);
            }

            if (producto.getTalle().equals(" ")) {
                producto.setTalle(null);
            }

            if (producto.getPeso().equals(" ")) {
                producto.setPeso(null);
            }
        }
    }
}

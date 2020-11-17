package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.dto.ProductoDTO;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
@Deprecated
public class ProductoArchivosHandlerImpl implements ProductoArchivosHandler {

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
            throw new ProductoException("Error al procesar el archivo CSV." + e.getMessage());
        }

    }

    @Override
    public List<ProductoDTO> recibirExcel(MultipartFile archivo){
        try {
            Workbook workbook = new XSSFWorkbook(archivo.getInputStream());

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<ProductoDTO> productos = new ArrayList<>();
            int rowNumber = 0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                ProductoDTO producto = new ProductoDTO();
                int cellIndex = 0;

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIndex) {
                        case 0:
                            producto.setNombre(currentCell.getStringCellValue());
                            break;
                        case 1:
                            producto.setDescripcion(currentCell.getStringCellValue());
                            break;
                        case 2:
                            producto.setPrecio(currentCell.getNumericCellValue());
                            break;
                        case 3:
                            producto.setSubcategoriaId((long) currentCell.getNumericCellValue());
                            break;
                        case 4:
                            producto.setMarcaId((long) currentCell.getNumericCellValue());
                            break;
                        case 5:
                            producto.setUnidadMedidaId((long) currentCell.getNumericCellValue());
                            break;
                        case 6:
                            producto.setDisponibilidad((int) currentCell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }

                    cellIndex++;
                }

                productos.add(producto);
            }

            workbook.close();
            return productos;
        } catch (IOException e) {
            throw new ProductoException("Error al procesar el archivo Excel.\n" + e.getMessage());
        }

    }

    @Override
    public List<ProductoDTO> recibirExcelActualizarStock(MultipartFile archivo) {
        try {
            Workbook workbook = new XSSFWorkbook(archivo.getInputStream());

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<ProductoDTO> productos = new ArrayList<>();
            int rowNumber = 0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                ProductoDTO producto = new ProductoDTO();

                int cellIndex = 0;

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIndex) {
                        case 0:
                            producto.setId((long) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            producto.setNombre(currentCell.getStringCellValue());
                            break;
                        case 2:
                            producto.setDescripcion(currentCell.getStringCellValue());
                            break;
                        case 3:
                            producto.setDisponibilidad((int) currentCell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }

                    cellIndex++;
                }

                productos.add(producto);
            }

            workbook.close();
            return productos;
        } catch (IOException e) {
            throw new ProductoException("Error al procesar el archivo Excel.\n " + e.getMessage());
        }
    }

    private void setearPropiedades(List<ProductoDTO> dtos) {
        for (ProductoDTO producto: dtos) {
            /*
            if (producto.getColor().equals(" ")) {
                producto.setColor(null);
            }

            if (producto.getTalle().equals(" ")) {
                producto.setTalle(null);
            }

            if (producto.getPeso().equals(" ")) {
                producto.setPeso(null);
            }

             */
        }
    }
}

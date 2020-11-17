package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.dto.ProductoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Esta clase se encarga de recibir y manejar los distintos archivos que se pueden
 * recibir para el manejo de productos (alta o actualizacion de productos).
 */

public interface ProductoArchivosHandler {

    /**
     * Recibe un archivo en formato .csv y convierte los datos en un listado
     * de Productos.
     * @param archivo Multipart file .csv con productos.
     * @return List listado de productos.
     */
    List<ProductoDTO> recibirCsv(MultipartFile archivo);

    /**
     * Recibe un archivo en formato .xlxs y convierte los datos en un listado
     * de Productos.
     * @param archivo Multipart file .xlxs con productos.
     * @return List listado de productos.
     */
    List<ProductoDTO> recibirExcel(MultipartFile archivo);

    /**
     * Recibe un archivo en formato .xlxs con productos YA EXISTENTES, con solo
     * datos que se utilizarán para la actualización masiva de disponibilidad o
     * nombres (futuramente tambien precio).
     * @param archivo Multipart file .xlxs con productos.
     * @return List listado de productos.
     */
    List<ProductoDTO> recibirExcelActualizarStock(MultipartFile archivo);
}

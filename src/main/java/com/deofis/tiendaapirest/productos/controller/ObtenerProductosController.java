package com.deofis.tiendaapirest.productos.controller;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exception.ProductoException;
import com.deofis.tiendaapirest.productos.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador que se encarga de obtener los productos de distintas formas.
 */

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ObtenerProductosController {

    private final ProductoService productoService;

    /**
     * Obtiene los productos ordenados alfabéticamente.
     * @return ResponseEntity con un listado de todos los productos.
     */
    @GetMapping("/productos")
    public ResponseEntity<?> obtenerProductos() {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productos;

        try {
            productos = this.productoService.obtenerProductos();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener los productos de la Base de Datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (productos.size() == 0) {
            response.put("error", "No existen productos registrados en la Base de Datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

}

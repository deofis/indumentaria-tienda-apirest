package com.deofis.tiendaapirest.productos.controller;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exception.ProductoException;
import com.deofis.tiendaapirest.productos.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador que se encarga de recibir un producto y registrarlo.
 */

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RegistrarProductoController {

    private final ProductoService productoService;

    @PostMapping("/productos")
    public ResponseEntity<?> registrarProducto(@RequestBody Producto producto) {

        Map<String, Object> response = new HashMap<>();
        Producto nuevoProducto;

        try {
            nuevoProducto = this.productoService.registrarProducto(producto);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al registrar el nuevo producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }
}

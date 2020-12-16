package com.deofis.tiendaapirest.productos.controllers.catalogoadmin.productos;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DestacarProductoController {

    private final ProductoService productoService;

    @PostMapping("/productos/destacar/{id}")
    public ResponseEntity<?> destacarProducto(@RequestBody Producto producto, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.productoService.destacar(producto, id);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al destacar/quitar al producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Producto destacado/quitado con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

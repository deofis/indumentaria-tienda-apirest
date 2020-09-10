package com.deofis.tiendaapirest.productos.controllers.producto;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase para probar los productos favoritos.
 */

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FavoritoController {

    private final ProductoService productoService;

    // API para marcar favorito un producto, sirve de prueba. Version final --> Es responsabilidad
    // del usuario manejar sus favoritos.
    @PostMapping("/productos/favorito/{id}")
    public ResponseEntity<?> marcarFavorito(@RequestBody Producto producto, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.productoService.marcarFavorito(producto, id);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al marcar/quitar de favoritos al producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Producto agregado/quitado de favoritos con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

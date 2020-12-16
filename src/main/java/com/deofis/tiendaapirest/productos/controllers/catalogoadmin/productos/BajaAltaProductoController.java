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
public class BajaAltaProductoController {

    private final ProductoService productoService;

    /**
     * Dar de baja lógica a un producto.
     * URL: ~/api/productos/baja/1
     * HttpMethod: POST
     * HttpStatus: OK
     * @param id PathVariable Long id del producto a dar de baja.
     * @return ResponseEntity con un mensaje String de la transacción exitosa.
     */
    @PostMapping("/productos/baja/{id}")
    public ResponseEntity<?> bajaProducto(@RequestBody Producto producto, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.productoService.darDeBaja(producto, id);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al dar de baja al producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Baja registrada correctamente.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Dar de alta a un producto.
     * URL: ~/api/productos/alta/1
     * HttpMehotd: POST
     * HttpStatus: OK
     * @param id PathVariable Long id del producto a activar.
     * @return ResponseEntity con mensaje String de transacción exitosa.
     */
    @PostMapping("/productos/alta/{id}")
    public ResponseEntity<?> altaProducto(@RequestBody Producto producto, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.productoService.darDeAlta(producto, id);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al dar de alta al producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Alta registrada correctamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

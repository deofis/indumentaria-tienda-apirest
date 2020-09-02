package com.deofis.tiendaapirest.productos.controller.producto;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exception.ProductoException;
import com.deofis.tiendaapirest.productos.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ActualizarProductoController {

    private final ProductoService productoService;

    /**
     * Modifica un producto seleccionado.
     * URL: ~/api/productos/1
     * HttpMethod: PUT
     * HttpStatus: CREATED
     * @param producto Producto ya modificado.
     * @param id PathVariable Long del producto a modificar.
     * @return ResponseEntity con el producto ya actualizado.
     */
    @PutMapping("/productos/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Producto producto, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Producto productoActualizado;

        try {
            productoActualizado = this.productoService.actualizar(producto, id);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al modificar el producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(productoActualizado, HttpStatus.CREATED);
    }
}

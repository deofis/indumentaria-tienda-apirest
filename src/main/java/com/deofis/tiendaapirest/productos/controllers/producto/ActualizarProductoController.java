package com.deofis.tiendaapirest.productos.controllers.producto;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ActualizarProductoController {

    private final ProductoService productoService;

    /**
     * Modifica un producto seleccionado.
     * URL: ~/api/productos/actualizar/1
     * HttpMethod: PUT
     * HttpStatus: CREATED
     * @param producto Producto ya modificado.
     * @param id PathVariable Long del producto a modificar.
     * @return ResponseEntity con el producto ya actualizado.
     */
    @PutMapping("/productos/actualizar/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Producto producto, @PathVariable Long id, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Producto productoActualizado;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("error", "Bad Request");
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

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

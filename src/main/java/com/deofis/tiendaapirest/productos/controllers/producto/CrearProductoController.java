package com.deofis.tiendaapirest.productos.controllers.producto;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador que se encarga de recibir un producto y registrarlo.
 */

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CrearProductoController {

    private final ProductoService productoService;

    /**
     * Registra un nuevo producto.
     * URL: ~/api/productos/nuevo
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @return ResponseEntity con el Producto guardado.
     */
    @PostMapping("/productos/nuevo")
    public ResponseEntity<?> crear(@Valid @RequestBody Producto producto, BindingResult result) {

        Map<String, Object> response = new HashMap<>();
        Producto nuevoProducto;

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
            nuevoProducto = this.productoService.crear(producto);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al registrar el nuevo producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }
}

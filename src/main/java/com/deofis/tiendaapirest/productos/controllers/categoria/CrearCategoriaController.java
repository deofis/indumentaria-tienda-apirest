package com.deofis.tiendaapirest.productos.controllers.categoria;

import com.deofis.tiendaapirest.productos.domain.Categoria;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
@Deprecated
public class CrearCategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Registrar una nueva categoria.
     * URL: ~/api/productos/categorias/nueva
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param categoria Categoria a guardar.
     * @return Categoria guardada.
     */
    @PostMapping("/productos/categorias/nueva")
    public ResponseEntity<?> crear(@Valid @RequestBody Categoria categoria, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Categoria nuevaCategoria;

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
            nuevaCategoria = this.categoriaService.crear(categoria);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al registrar nueva categoría");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

}

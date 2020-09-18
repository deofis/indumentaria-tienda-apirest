package com.deofis.tiendaapirest.productos.controllers.categoria;

import com.deofis.tiendaapirest.productos.domain.Categoria;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
public class ActualizarCategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Actualizar una categoría.
     * URL: ~/api/productos/categorias/actualizar/1
     * HttpMethod: PUT
     * HttpStatus: CREATED
     * @param categoria Categoría actualizada
     * @param id PathVariable Long id de la categoría a actualizar.
     * @return ResponseEntity con la categoría actualizada.
     */
    @PutMapping("/productos/categorias/actualizar/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Categoria categoria, @PathVariable Long id, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Categoria categoriaActualizada;

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
            categoriaActualizada = this.categoriaService.actualizar(categoria, id);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al actualizar la categoría");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(categoriaActualizada, HttpStatus.CREATED);
    }

}

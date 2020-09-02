package com.deofis.tiendaapirest.productos.controller.categoria;

import com.deofis.tiendaapirest.productos.domain.Categoria;
import com.deofis.tiendaapirest.productos.exception.ProductoException;
import com.deofis.tiendaapirest.productos.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
public class ActualizarCategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Actualizar una categoría.
     * URL: ~/api/productos/categorias/1
     * HttpMethod: PUT
     * HttpStatus: CREATED
     * @param categoria Categoría actualizada
     * @param id PathVariable Long id de la categoría a actualizar.
     * @return ResponseEntity con la categoría actualizada.
     */
    @PutMapping("/productos/categorias/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Categoria categoria, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Categoria categoriaActualizada;

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

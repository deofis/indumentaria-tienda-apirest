package com.deofis.tiendaapirest.productos.controller.categoria;

import com.deofis.tiendaapirest.productos.domain.Categoria;
import com.deofis.tiendaapirest.productos.exception.ProductoException;
import com.deofis.tiendaapirest.productos.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
public class CrearCategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Registrar una nueva categoria.
     * URL: ~/api/productos/categoria
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param categoria Categoria a guardar.
     * @return Categoria guardada.
     */
    @PostMapping("/productos/categorias")
    public ResponseEntity<?> crear(@Valid @RequestBody Categoria categoria) {
        Map<String, Object> response = new HashMap<>();
        Categoria nuevaCategoria;

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

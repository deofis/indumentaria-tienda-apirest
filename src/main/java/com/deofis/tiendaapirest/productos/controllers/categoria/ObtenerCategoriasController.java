package com.deofis.tiendaapirest.productos.controllers.categoria;

import com.deofis.tiendaapirest.productos.domain.Categoria;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ObtenerCategoriasController {

    private final CategoriaService categoriaService;

    /**
     * Obtener un listado de categorias ordenadas por nombre.
     * URL: ~/api/productos/categorias
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con todas las categorias.
     */
    @GetMapping("/productos/categorias")
    public ResponseEntity<?> obtenerCategorias() {
        Map<String, Object> response = new HashMap<>();
        List<Categoria> categorias;

        try {
            categorias = this.categoriaService.obtenerCategorias();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener el listado de categorias");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (categorias.size() == 0) {
            response.put("mensaje", "No existen categorías en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    /**
     * Obtener una categoría específica.
     * URL: ~/api/productos/categorias/1
     * HttpMethod: GET
     * HttpStatus: OK
     * @param id PathVariable Long del id de la categoría a obtener.
     * @return ResponseEntity con la categoría.
     */
    @GetMapping("/productos/categorias/{id}")
    public ResponseEntity<?> obtenerCategoria(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Categoria categoria;

        try {
            categoria = this.categoriaService.obtenerCategoria(id);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener la categoría");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

}

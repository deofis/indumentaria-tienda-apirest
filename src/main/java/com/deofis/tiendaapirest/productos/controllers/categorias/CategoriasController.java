package com.deofis.tiendaapirest.productos.controllers.categorias;

import com.deofis.tiendaapirest.productos.domain.Categoria;
import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.CategoriaService;
import com.deofis.tiendaapirest.productos.services.SubcategoriaService;
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

/**
 * Controlador que se encarga de listar y obtener las categorías y subcategorías, así
 * como las propiedades de las subcategorías.
 */

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CategoriasController {

    private final CategoriaService categoriaService;
    private final SubcategoriaService subcategoriaService;

    /**
     * Obtener un todas las categorias ordenadas por nombre.
     * URL: ~/api/categorias
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con todas las categorias.
     */
    @GetMapping("/categorias")
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

        response.put("categorias", categorias);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtener una categoría específica.
     * URL: ~/api/categorias/ver/1
     * HttpMethod: GET
     * HttpStatus: OK
     * @param categoriaId PathVariable Long del id de la categoría a obtener.
     * @return ResponseEntity con la categoría.
     */
    @GetMapping("/categorias/{categoriaId}")
    public ResponseEntity<?> obtenerCategoria(@PathVariable Long categoriaId) {
        Map<String, Object> response = new HashMap<>();
        Categoria categoria;

        try {
            categoria = this.categoriaService.obtenerCategoria(categoriaId);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener la categoría");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("categoria", categoria);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene el todas las subcategorias que pertenecen a la categoria solicitada.
     * URL: ~/api/categorias/1/subcategorias
     * HttpMethod: GET
     * HttpStatus: OK
     * @param categoriaId PathVariable Long con el id de la categoria a consultar sus subcategorias.
     * @return ResponseEntity con las subcategorias.
     */
    @GetMapping("/categorias/{categoriaId}/subcategorias")
    public ResponseEntity<?> obtenerSubcategoriasDeCategoria(@PathVariable Long categoriaId) {
        Map<String, Object> response = new HashMap<>();
        List<Subcategoria> subcategorias;
        String categoria;

        try {
            subcategorias = this.categoriaService.obtenerSubcategorias(categoriaId);
            categoria = this.categoriaService.obtenerCategoria(categoriaId).getNombre();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener las subcategorias de la categoría");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("categoria", categoria);
        response.put("subcategorias", subcategorias);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene una {@link Subcategoria} requerida, que pertenece a una {@link Categoria} requerida.
     * URL: ~/api/categorias/1/subcategorias/1
     * HttpMethod: GET
     * HttpStatus: OK
     * @param categoriaId PathVariable Long id de la categoría a la que pertenece la subcategoría.
     * @param subcategoriaId PathVariable Long id de la subcategoría.
     * @return ResponseEntity con la subcategoría y el nombre de la categoría.
     */
    @GetMapping("/categorias/{categoriaId}/subcategorias/{subcategoriaId}")
    public ResponseEntity<?> obtenerSubcategoriaDeCategoria(@PathVariable Long categoriaId,
                                                            @PathVariable Long subcategoriaId) {

        Map<String, Object> response = new HashMap<>();
        Subcategoria subcategoria;
        String categoria;

        try {
            subcategoria = this.categoriaService.obtenerSubcategoriaDeCategoria(categoriaId, subcategoriaId);
            categoria = this.categoriaService.obtenerCategoria(categoriaId).getNombre();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener la subcategoría de la categoría");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("categoria", categoria);
        response.put("subcategoria", subcategoria);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene todas las subcategorias.
     * URL: ~/api/subcategorias
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con las subcategorias.
     */
    @GetMapping("/subcategorias")
    public ResponseEntity<?> obtenerSubcategorias() {
        Map<String, Object> response = new HashMap<>();
        List<Subcategoria> subcategorias;

        try {
            subcategorias = this.subcategoriaService.obtenerSubcategorias();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener las subcategorias");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("subcategorias", subcategorias);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene una subcategoria especifica.
     * URL: ~/api/subcategorias/1
     * HttpMethod: GET
     * HttpStatus: OK
     * @param subcategoriaId Long id de la subcategoria requerida.
     * @return ResponsEntity con la subcategoria.
     */
    @GetMapping("/subcategorias/{subcategoriaId}")
    public ResponseEntity<?> obtenerSubcategoria(@PathVariable Long subcategoriaId) {
        Map<String, Object> response = new HashMap<>();
        Subcategoria subcategoria;

        try {
            subcategoria = this.subcategoriaService.obtenerSubcategoria(subcategoriaId);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener la subcategoria");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("subcategoria", subcategoria);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene una lista con todas las propiedades de una subcategoría requerida.
     * URL: ~/api/subcategorias/1/propiedades
     * HttpMethod: GET
     * HttpStatus: OK
     * @param subcategoriaId Long id de la subcategoria requerida.
     * @return ResponseEntity con el nombre de la subcategoria y la lista de propiedades obtenidas.
     */
    @GetMapping("/subcategorias/{subcategoriaId}/propiedades")
    public ResponseEntity<?> obtenerPropiedadesSubcategoria(@PathVariable Long subcategoriaId) {
        Map<String, Object> response = new HashMap<>();
        List<PropiedadProducto> propiedades;
        String subcategoria;

        try {
            propiedades = this.subcategoriaService.obtenerPropiedadesSubcategoria(subcategoriaId);
            subcategoria = this.subcategoriaService.obtenerSubcategoria(subcategoriaId).getNombre();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener las propiedades de la subcategoria");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("subcategoria", subcategoria);
        response.put("propiedades", propiedades);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

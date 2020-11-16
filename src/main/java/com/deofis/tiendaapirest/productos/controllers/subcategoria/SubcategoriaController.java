package com.deofis.tiendaapirest.productos.controllers.subcategoria;

import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.Subcategoria;
import com.deofis.tiendaapirest.productos.domain.ValorPropiedadProducto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.SubcategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SubcategoriaController {

    private final SubcategoriaService subcategoriaService;

    /**
     * Lista todas las subcategorias existentes.
     * URL: ~/api/subcategorias
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con la lista de subcategorias.
     */
    @GetMapping("/subcategorias")
    public ResponseEntity<List<Subcategoria>> listarSubcategorias() {
        return new ResponseEntity<>(this.subcategoriaService.listarSubcategorias(), HttpStatus.OK);
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
     * Crea una nueva propiedad para una subcategoria requerida.
     * URL: ~/api/subcategorias/1/propiedades
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param subcategoriaId Long id de la subcategoria a agregar la propiedad nueva.
     * @param propiedadProducto Propiedad nueva.
     * @return ResponseEntity con la subcategoria actualizada.
     */
    @PostMapping("/subcategorias/{subcategoriaId}/propiedades")
    public ResponseEntity<?> agregarPropiedad(@PathVariable Long subcategoriaId, @RequestBody PropiedadProducto propiedadProducto) {
        Map<String, Object> response = new HashMap<>();
        Subcategoria subcategoriaActualizada;

        try {
            subcategoriaActualizada = this.subcategoriaService.agregarPropiedad(subcategoriaId, propiedadProducto);
        }  catch (ProductoException e) {
            response.put("mensaje", "Error al agregar la propiedad");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("subcategoria", subcategoriaActualizada);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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

    /**
     * Agrega un nuevo valor a una propiedad existente, de una subcategoria requerida.
     * URL: ~/api/subcategorias/1/propiedades/1/valores
     * HttpMethod: POST
     * HttpStatud: CREATED
     * @param subcategoriaId Long id de la subcategoria requerida.
     * @param propiedadId Long id de la propiedad a agrega el nuevo valor.
     * @param valorPropiedadProducto ValorPropiedad nueva a agregar.
     * @return ResponseEntity con el nombre de la subcategoria y la propiedad actualizada.
     */
    @PostMapping("/subcategorias/{subcategoriaId}/propiedades/{propiedadId}/valores")
    public ResponseEntity<?> agregarValorAPropiedad(@PathVariable Long subcategoriaId,
                                                    @PathVariable Long propiedadId,
                                                    @RequestBody ValorPropiedadProducto valorPropiedadProducto) {

        Map<String, Object> response = new HashMap<>();
        PropiedadProducto propiedadProductoActualizada;
        String subcategoria;

        try {
            propiedadProductoActualizada = this.subcategoriaService.agregarValor(subcategoriaId, propiedadId, valorPropiedadProducto);
            subcategoria = this.subcategoriaService.obtenerSubcategoria(subcategoriaId).getNombre();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al agregar valor a la propiedad de la subcategoria");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("subcategoria", subcategoria);
        response.put("propiedad", propiedadProductoActualizada);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtiene los valores de una propiedad que pertenece a una subcategoria requerida.
     * URL: ~/api/subcategorias/1/propiedades/1/valores
     * HttpMethod: GET
     * HttpStatus: OK
     * @param subcategoriaId Long id de la subcategoria requerida.
     * @param propiedadId Long id de la propiedad requerida a buscar valores.
     * @return ResponseEntity con el nombre de la subcategoria y propiedad, y lista de los valores de estos.
     */
    @GetMapping("/subcategorias/{subcategoriaId}/propiedades/{propiedadId}/valores")
    public ResponseEntity<?> obtenerValoresPropiedad(@PathVariable Long subcategoriaId,
                                                     @PathVariable Long propiedadId) {

        Map<String, Object> response = new HashMap<>();
        List<ValorPropiedadProducto> valores;
        String propiedad;
        String subcategoria;

        try {
            valores = this.subcategoriaService.obtenerValoresPropiedad(subcategoriaId, propiedadId);
            propiedad = this.subcategoriaService.obtenerPropiedad(subcategoriaId, propiedadId).getNombre();
            subcategoria = this.subcategoriaService.obtenerSubcategoria(subcategoriaId).getNombre();
        } catch (ProductoException e) {
            response.put("mensaje", "Error al obtener los valores de la propiedad de la subcategoria");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("subcategoria", subcategoria);
        response.put("propiedad", propiedad);
        response.put("valores", valores);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

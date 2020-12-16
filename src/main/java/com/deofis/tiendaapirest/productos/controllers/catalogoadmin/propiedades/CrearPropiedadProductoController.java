package com.deofis.tiendaapirest.productos.controllers.catalogoadmin.propiedades;

import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.CatalogoAdminService;
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
public class CrearPropiedadProductoController {

    private final CatalogoAdminService catalogoAdminService;

    @PostMapping("/productos/propiedades")
    public ResponseEntity<?> crearPropiedadProducto(@RequestBody PropiedadProducto propiedadProducto) {
        Map<String, Object> response = new HashMap<>();
        PropiedadProducto nuevaPropiedad;

        try {
            nuevaPropiedad = this.catalogoAdminService.crearPropiedadProducto(propiedadProducto);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al crear nueva propiedad de producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("propiedadProducto", nuevaPropiedad);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/productos/{productoId}/propiedades")
    public ResponseEntity<?> crearPropiedadProducto(@PathVariable Long productoId,
                                                    @Valid @RequestBody PropiedadProducto propiedadProducto,
                                                    BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        PropiedadProducto nuevaPropiedad;

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
            nuevaPropiedad = this.catalogoAdminService.crearPropiedadProducto(productoId, propiedadProducto);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al crear nueva propiedad de producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("propiedadProducto", nuevaPropiedad);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
    public ResponseEntity<?> crearPropiedadProductoSubcategoria(@PathVariable Long subcategoriaId,
                                                                @RequestBody PropiedadProducto propiedadProducto) {
        Map<String, Object> response = new HashMap<>();
        PropiedadProducto nuevaPropiedad;

        try {
            nuevaPropiedad = this.catalogoAdminService.crearPropiedadProductoSubcategoria(subcategoriaId, propiedadProducto);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al crear nueva propiedad de producto para la subcategoria");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("propiedadProducto", nuevaPropiedad);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

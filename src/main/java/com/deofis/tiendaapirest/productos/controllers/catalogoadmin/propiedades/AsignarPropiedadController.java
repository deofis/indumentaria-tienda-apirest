package com.deofis.tiendaapirest.productos.controllers.catalogoadmin.propiedades;

import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.CatalogoAdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AsignarPropiedadController {

    private final CatalogoAdminService catalogoAdminService;

    @GetMapping("/productos/{productoId}/propiedades/{propiedadId}/asignar")
    public ResponseEntity<?> asignarPropiedadAProducto(@PathVariable Long productoId,
                                                       @PathVariable Long propiedadId) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.catalogoAdminService.asignarPropiedadAProducto(productoId, propiedadId);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al asignar la propiedad al producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Propiedad asignada con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/subcategorias/{subcategoriaId}/propiedades/{propiedadId}/asignar")
    public ResponseEntity<?> asignarPropiedadASubcategoria(@PathVariable Long subcategoriaId,
                                                           @PathVariable Long propiedadId) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.catalogoAdminService.asignarPropiedadASubcategoria(subcategoriaId, propiedadId);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al asignar la propiedad a la subcategoría");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Propiedad asignada con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

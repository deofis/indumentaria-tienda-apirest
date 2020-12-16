package com.deofis.tiendaapirest.productos.controllers.catalogoadmin.propiedades;

import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.CatalogoAdminService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EliminarPropiedadController {

    private final CatalogoAdminService catalogoAdminService;

    /**
     * Elimina, si es posible, una propiedad producto des-referenciada del sistema.
     * URL: ~/api/productos/propiedades/1
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @param propiedadId PathVariable Long id de la propiedad a eliminar.
     * @return ResponseEntity mensaje eliminación/error.
     */
    @DeleteMapping("/productos/propiedades/{propiedadId}")
    public ResponseEntity<?> eliminarPropiedadProducto(@PathVariable Long propiedadId) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.catalogoAdminService.eliminarPropiedadProducto(propiedadId);
        } catch (ProductoException | DataAccessException e) {
            response.put("mensaje", "Error al eliminar la propiedad producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Propiedad producto eliminada con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

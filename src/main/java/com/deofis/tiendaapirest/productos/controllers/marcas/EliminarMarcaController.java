package com.deofis.tiendaapirest.productos.controllers.marcas;

import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.MarcaService;
import lombok.AllArgsConstructor;
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
public class EliminarMarcaController {

    private final MarcaService marcaService;

    /**
     * Se encarga de eliminar una marca, en caso de que no tenga referencias con otros objetos, es decir,
     * que no esté siendo utilizada por ningun objeto en el sistema.
     * URL: ~/api/productos/marcas/1
     * HttpMethod: DELETE
     * HttpStatus: OK
     * @param marcaId PathVariable Long id de la marca a eliminar.
     * @return ResponseEntity con mensaje eliminación/error.
     */
    @DeleteMapping("/productos/marcas/{marcaId}")
    public ResponseEntity<?> eliminarMarca(@PathVariable Long marcaId) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.marcaService.eliminarMarca(marcaId);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al eliminar marca");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Marca eliminada con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

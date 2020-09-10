package com.deofis.tiendaapirest.productos.controllers.marca;

import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.MarcaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ActualizarMarcaController {

    private final MarcaService marcaService;

    /**
     * Actualizar una marca.
     * URL: ~/api/productos/marcas/1
     * HttpMethod: PUT
     * HttpStatus: CREATED
     * @param marca Marca actualizada.
     * @param id PathVariable id de la marca a actualizar.
     * @return ResponseEntity con la marca actualizada.
     */
    @PutMapping("/productos/marcas/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Marca marca, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Marca marcaActualizada;

        try {
            marcaActualizada = this.marcaService.actualizar(marca, id);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al actualizar la marca");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(marcaActualizada, HttpStatus.CREATED);
    }

}

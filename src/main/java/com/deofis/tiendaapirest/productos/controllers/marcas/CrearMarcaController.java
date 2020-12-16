package com.deofis.tiendaapirest.productos.controllers.marcas;

import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.services.MarcaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CrearMarcaController {

    private final MarcaService marcaService;

    /**
     * Registrar nueva marca.
     * URL: ~/api/productos/marcas
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param marca Marca a crear.
     * @return Marca registrada.
     */
    @PostMapping("/productos/marcas")
    public ResponseEntity<?> crear(@Valid @RequestBody Marca marca, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Marca nuevaMarca;

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
            nuevaMarca = this.marcaService.crearMarca(marca);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al registrar la nueva marca");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(nuevaMarca, HttpStatus.CREATED);
    }

}

package com.deofis.tiendaapirest.operacion.controllers;

import com.deofis.tiendaapirest.operacion.domain.Operacion;
import com.deofis.tiendaapirest.operacion.exceptions.OperacionException;
import com.deofis.tiendaapirest.operacion.services.OperacionService;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RegistrarOperacionController {

    private final OperacionService operacionService;

    /**
     * Registra una nueva operación de compra/venta para el cliente que se pasa como parte de la
     * operación en sí.
     * URL: ~/api/operaciones/nueva
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param operacion RequestBody con la operacion a registrar.
     * @return ResponseEntity con la operacion registrada.
     */
    @PostMapping("/operaciones/nueva")
    public ResponseEntity<?> registrarOperacion(@Valid @RequestBody Operacion operacion) {
        Map<String, Object> response = new HashMap<>();
        Operacion nuevaOperacion;

        try {
            nuevaOperacion = this.operacionService.registrarNuevaOperacion(operacion);
        } catch (OperacionException | ProductoException e) {
            response.put("mensahe", "Error al registrar la nueva compra");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("compra", nuevaOperacion);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}

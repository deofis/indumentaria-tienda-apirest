package com.deofis.tiendaapirest.operaciones.controllers;

import com.deofis.tiendaapirest.clientes.exceptions.ClienteException;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.exceptions.OperacionException;
import com.deofis.tiendaapirest.operaciones.services.CompraService;
import com.deofis.tiendaapirest.perfiles.exceptions.PerfilesException;
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
public class ComprasController {

    private final CompraService compraService;

    /**
     * Ver el historial de compras del cliente del perfil actual.
     * URL: ~/api/perfiles/compras/ver
     * HttpMethod: GET
     * HttpStatus OK
     * @return ResponseEntity List con las operaciones del perfil.
     */
    @GetMapping("/perfil/compras/historial")
    public ResponseEntity<?> verHistorialCompras() {
        Map<String, Object> response = new HashMap<>();
        List<Operacion> compras;

        try {
            compras = this.compraService.historialCompras();
        } catch (PerfilesException | ClienteException | OperacionException e) {
            response.put("mensaje", "Error al obtener el historial de compras del perfil");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (compras.size() == 0) {
            response.put("error", "El historial de compras del perfil esta vacío");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("compras", compras);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Ver una compra en particular.
     * URL: ~/api/perfiles/compras/1
     * HttpMethod: GET
     * HttpStatus: OK
     * @param nroOperacion @PathVariable Long numero de operación.
     * @return ResponseEntity Operacion seleccionada.
     */
    @GetMapping("/perfil/compras/ver/{nroOperacion}")
    public ResponseEntity<?> verCompra(@PathVariable Long nroOperacion) {
        Map<String, Object> response = new HashMap<>();
        Operacion compra;

        try {
            compra = this.compraService.verCompra(nroOperacion);
        } catch (PerfilesException | ClienteException | OperacionException e) {
            response.put("mensaje", "Error al obtener la compra");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("compra", compra);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

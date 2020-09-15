package com.deofis.tiendaapirest.operacion.controllers;

import com.deofis.tiendaapirest.operacion.domain.Operacion;
import com.deofis.tiendaapirest.operacion.exceptions.OperacionException;
import com.deofis.tiendaapirest.operacion.services.OperacionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ObtenerVentasController {

    private final OperacionService operacionService;

    /**
     * Obtener un listado de todas las operaciones (ventas) registradas en el sistema.
     * URL: ~/api/operaciones
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity List de todas las operaciones ordenadas por fecha.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/operaciones")
    public ResponseEntity<?> listarVentas() {
        Map<String, Object> response = new HashMap<>();
        List<Operacion> ventas;

        try {
            ventas = this.operacionService.listarVentas();
        } catch (OperacionException e) {
            response.put("mensaje", "Error al obtener el listado de ventas");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ventas.size() == 0) {
            response.put("error", "No existen ventas registradas hasta la fecha");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("totalVentas", ventas.size());
        response.put("ventas", ventas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

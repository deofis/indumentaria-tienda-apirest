package com.deofis.tiendaapirest.operaciones.controllers;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.exceptions.OperacionException;
import com.deofis.tiendaapirest.operaciones.services.VentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
public class ObtenerVentasController {

    private final VentaService ventaService;

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
            ventas = this.ventaService.listarVentas();
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

    /**
     * Como administrador, poder obtener una venta en particular.
     * URL: ~/api/operaciones/ver/1
     * HttpMethod: GET
     * HttpStatus: OK
     * @param nroOperacion @PathVariable Long con el numero de operación que se desea ver.
     * @return ResponseEntity Operacion solicitada.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/operaciones/ver/{nroOperacion}")
    public ResponseEntity<?> obtenerVenta(@PathVariable Long nroOperacion) {
        Map<String, Object> response = new HashMap<>();
        Operacion operacion;

        try {
            operacion = this.ventaService.obtenerVenta(nroOperacion);
        } catch (OperacionException e) {
            response.put("mensaje", "Error al obtener la venta");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("operacion", operacion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

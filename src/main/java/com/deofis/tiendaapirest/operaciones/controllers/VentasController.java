package com.deofis.tiendaapirest.operaciones.controllers;

import com.deofis.tiendaapirest.operaciones.domain.EstadoOperacion;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.exceptions.OperacionException;
import com.deofis.tiendaapirest.operaciones.services.VentaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class VentasController {

    private final VentaService ventaService;

    /**
     * Obtener un listado de todas las operaciones (ventas) registradas en el sistema.
     * URL: ~/api/ventas
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity List de todas las operaciones ordenadas por fecha.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/ventas")
    public ResponseEntity<?> listarVentas(@RequestParam(name = "estado", required = false) String estado,
                                          @RequestParam(name = "fechaDesde", required = false) Date fechaDesde,
                                          @RequestParam(name = "fechaHasta", required = false) Date fechaHasta) {
        Map<String, Object> response = new HashMap<>();
        List<Operacion> ventas = null;
        Double montoTotal;

        log.info(estado);
        log.info(String.valueOf(fechaDesde));
        log.info(String.valueOf(fechaHasta));

        if (estado != null) {
            try {
                if (estado.equalsIgnoreCase(String.valueOf(EstadoOperacion.PAYMENT_PENDING)))
                    ventas = this.ventaService.ventasPendientePago();
                else if (estado.equalsIgnoreCase(String.valueOf(EstadoOperacion.PAYMENT_DONE)))
                    ventas = this.ventaService.ventasCompletadoPago();
                else if (estado.equalsIgnoreCase(String.valueOf(EstadoOperacion.SENT)))
                    ventas = this.ventaService.ventasEnviadas();
                else if (estado.equalsIgnoreCase(String.valueOf(EstadoOperacion.RECEIVED)))
                    ventas = this.ventaService.ventasRecibidas();
                else if (estado.equalsIgnoreCase(String.valueOf(EstadoOperacion.CANCELLED)))
                    ventas = this.ventaService.ventasCanceladas();
            } catch (OperacionException e) {
                response.put("mensaje", "Error al obtener el listado de ventas");
                response.put("error", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (fechaDesde != null) {
            if (fechaHasta == null) {
                response.put("mensaje", "Error al obtener el listado de ventas");
                response.put("error", "Debe ingresar ambas fechas");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            try {
                ventas = this.ventaService.ventasFecha(fechaDesde, fechaHasta);
            } catch (OperacionException e) {
                response.put("mensaje", "Error al obtener el listado de ventas");
                response.put("error", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (fechaHasta != null) {
            response.put("mensaje", "Error al obtener el listado de ventas");
            response.put("error", "Debe ingresar ambas fechas");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (estado == null && fechaDesde == null) {
            try {
                ventas = this.ventaService.listarVentas();
            } catch (OperacionException e) {
                response.put("mensaje", "Error al obtener el listado de ventas");
                response.put("error", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (estado != null && fechaDesde != null) {
            ventas = this.ventaService.ventasFechaYEstado(estado, fechaDesde, fechaHasta);
        }

        if (ventas == null) {
            response.put("mensaje", "Error al obtener el listado de ventas");
            response.put("error", "El estado solicitado no existe, o ha sido tipeado de manera equivocada");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        montoTotal = 0.00;
        for (Operacion venta: ventas) {
            montoTotal += venta.getTotal();
        }

        response.put("totalVentas", ventas.size());
        response.put("montoTotal", montoTotal);
        response.put("estado", estado);
        response.put("fechaDesde", fechaDesde);
        response.put("fechaHasta", fechaHasta);
        response.put("ventas", ventas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene un listado con todas las ventas realizadas en el sistema a partir de un estado
     * requerido por el administrador.
     * URL: ~/api/ventas/estados
     * HttpMethod: GET
     * HttpStatus: OK
     * @param estado RequestParam  String nombre del estado solicitado.
     * @return ResponseEntity con la lista de operaciones en el estado requerido.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/ventas/estados")
    public ResponseEntity<?> ventasEstado(@RequestParam(name = "estado") String estado) {
        Map<String, Object> response = new HashMap<>();
        List<Operacion> ventasEstado;
        Double montoTotal;

        try {
            if (estado.equalsIgnoreCase(String.valueOf(EstadoOperacion.PAYMENT_PENDING)))
                ventasEstado = this.ventaService.ventasPendientePago();
            else if (estado.equalsIgnoreCase(String.valueOf(EstadoOperacion.PAYMENT_DONE)))
                ventasEstado = this.ventaService.ventasCompletadoPago();
            else if (estado.equalsIgnoreCase(String.valueOf(EstadoOperacion.SENT)))
                ventasEstado = this.ventaService.ventasEnviadas();
            else if (estado.equalsIgnoreCase(String.valueOf(EstadoOperacion.RECEIVED)))
                ventasEstado = this.ventaService.ventasRecibidas();
            else if (estado.equalsIgnoreCase(String.valueOf(EstadoOperacion.CANCELLED)))
                ventasEstado = this.ventaService.ventasCanceladas();
            else
                ventasEstado = null;

        } catch (OperacionException e) {
            response.put("mensaje", "Error al obtener las ventas por estado");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ventasEstado == null) {
            response.put("mensaje", "Error al obtener las ventas por estado");
            response.put("error", "El estado solicitado no existe o ha sido tipeado de manera equivocada");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        montoTotal = 0.00;
        for (Operacion venta: ventasEstado) {
            montoTotal += venta.getTotal();
        }

        response.put("montoTotal", montoTotal);
        response.put("ventas", ventasEstado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene un listado con las ventas dentro del rango de fechas solicitado por el administrador.
     * URL: ~/api/ventas/fechas
     * HttpMethod: GET
     * HttpStatus: OK
     * @param fechaDesde RequestParam Date fecha desde.
     * @param fechaHasta RequestParam Date fecha hasta.
     * @return ResponseEntity con el listado de operaciones que cumplen con las fechas requeridas.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/ventas/fechas")
    public ResponseEntity<?> ventasEnFecha(@RequestParam(name = "fechaDesde") Date fechaDesde,
                                           @RequestParam(name = "fechaHasta") Date fechaHasta) {
        Map<String, Object> response = new HashMap<>();
        List<Operacion> ventasFecha;
        Double montoTotal;

        try {
            ventasFecha = this.ventaService.ventasFecha(fechaDesde, fechaHasta);
        } catch (OperacionException e) {
            response.put("mensaje", "Error al obtener las ventas en las fechas solicitadas");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        montoTotal = 0.00;
        for (Operacion venta: ventasFecha) {
            montoTotal += venta.getTotal();
        }

        response.put("montoTotal", montoTotal);
        response.put("ventas", ventasFecha);
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

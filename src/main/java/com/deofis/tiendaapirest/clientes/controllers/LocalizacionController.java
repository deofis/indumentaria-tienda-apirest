package com.deofis.tiendaapirest.clientes.controllers;

import com.deofis.tiendaapirest.clientes.domain.Ciudad;
import com.deofis.tiendaapirest.clientes.domain.Estado;
import com.deofis.tiendaapirest.clientes.domain.Pais;
import com.deofis.tiendaapirest.clientes.exceptions.PaisesException;
import com.deofis.tiendaapirest.clientes.services.LocalizacionService;
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
public class LocalizacionController {

    private final LocalizacionService localizacionService;

    /**
     * Lista todos los paises de forma ordenada de menor a mayor.
     * URL: ~/api/paises
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con el listado de paises.
     */
    @GetMapping("/paises")
    public ResponseEntity<Map<String, Object>> listarPaises() {
        Map<String, Object> response = new HashMap<>();
        response.put("paises", this.localizacionService.listarPaises());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtiene un país requerido.
     * URL: ~/api/paises/Argentina
     * HttpMethod: GET
     * HttpStatus: OK
     * @param nombrePais PathVariable String con el nombre del país.
     * @return ResponseEntity con el país.
     */
    @GetMapping("/paises/{nombrePais}")
    public ResponseEntity<?> obtenerPais(@PathVariable String nombrePais) {
        Map<String, Object> response = new HashMap<>();
        Pais pais;

        try {
            pais = this.localizacionService.obtenerPais(nombrePais);
        } catch (PaisesException e) {
            response.put("mensaje", "Error al obtener el pa+is");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("pais", pais);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Lista todos los Estados que pertenecen a un País requerido por id.
     * URL: ~/api/paises/11/estados
     * HttpMethod: GET
     * HttpStatus: OK
     * @param paisId PathVariable String nombre del país a listar sus estados.
     * @return ResponseEntity listado de estados del país.
     */
    @GetMapping("/paises/{paisId}/estados")
    public ResponseEntity<?> listarEstadosDePais(@PathVariable Long paisId) {
        Map<String, Object> response = new HashMap<>();
        List<Estado> estadosDePais;
        String pais;

        try {
            estadosDePais = this.localizacionService.estadosDePais(paisId);
            pais = this.localizacionService.obtenerPais(paisId).getNombre();
        } catch (PaisesException e) {
            response.put("mensaje", "Error al obtener los estados del pais ");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("pais", pais);
        response.put("estados", estadosDePais);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/paises/{paisId}/estados/{estadoId}/ciudades")
    public ResponseEntity<?> listarCiudadesDeEstado(@PathVariable Long paisId,
                                                    @PathVariable Long estadoId) {
        Map<String, Object> response = new HashMap<>();
        List<Ciudad> ciudadesPais;
        String estado;
        String pais;

        try {
            ciudadesPais = this.localizacionService.ciudadesEstado(paisId, estadoId);
        } catch (PaisesException e) {
            response.put("mensaje", "Error al obtener las ciudades del estado");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("ciudades", ciudadesPais);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

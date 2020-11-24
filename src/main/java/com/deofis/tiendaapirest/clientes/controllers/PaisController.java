package com.deofis.tiendaapirest.clientes.controllers;

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
public class PaisController {

    private final LocalizacionService localizacionService;

    /**
     * Lista todos los paises de forma ordenada de menor a mayor.
     * URL: ~/api/paises
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con el listado de paises.
     */
    @GetMapping("/paises")
    public ResponseEntity<List<Pais>> listarPaises() {
        return new ResponseEntity<>(this.localizacionService.listarPaises(), HttpStatus.OK);
    }

    @GetMapping("/paises/{nombrePais}/estados")
    public ResponseEntity<?> listarEstadosDePais(@PathVariable String nombrePais) {
        Map<String, Object> response = new HashMap<>();
        List<Estado> estados;

        try {
            estados = this.localizacionService.estadosDePais(nombrePais);
        } catch (PaisesException e) {
            response.put("mensaje", "Error al obtener los estados del pais " + nombrePais);
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("estados", estados);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

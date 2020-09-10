package com.deofis.tiendaapirest.clientes.controllers;

import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.clientes.exceptions.ClienteException;
import com.deofis.tiendaapirest.clientes.services.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ObtenerClientesController {

    private final ClienteService clienteService;

    @GetMapping("/clientes/{clienteId}")
    public ResponseEntity<?> obtenerCliente(@PathVariable Long clienteId) {
        Map<String, Object> response = new HashMap<>();
        Cliente cliente;

        try {
            cliente = this.clienteService.obtenerCliente(clienteId);
        } catch (ClienteException e) {
            response.put("mensaje", "Error al obtener el cliente");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

}

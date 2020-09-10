package com.deofis.tiendaapirest.clientes.controllers;

import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.clientes.exceptions.ClienteException;
import com.deofis.tiendaapirest.clientes.services.ClienteService;
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

// No es responsabilidad del Usuario registrase como CLIENTE ?? --> SI: CREARLO EN CONTROLADOR USUARIOS.
//                                                                  NO: DEJARLO ACA.

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RegistrarClienteController {

    private final ClienteService clienteService;

    @PostMapping("/clientes/nuevo")
    public ResponseEntity<?> crear(@Valid @RequestBody Cliente cliente) {
        Map<String, Object> response = new HashMap<>();
        Cliente nuevoCliente;

        try {
            nuevoCliente = this.clienteService.crear(cliente);
        } catch (ClienteException e) {
            response.put("mensaje", "Error al crear el nuevo cliente");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

}

package com.deofis.tiendaapirest.clientes.controllers;

import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.clientes.exceptions.ClienteException;
import com.deofis.tiendaapirest.clientes.services.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ActualizarClienteController {

    private final ClienteService clienteService;

    @PutMapping("/clientes/actualizar/{clienteId}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Cliente cliente, @PathVariable Long clienteId) {
        Map<String, Object> response = new HashMap<>();
        Cliente clienteActualizado;

        try {
            clienteActualizado = this.clienteService.actualizar(cliente, clienteId);
        } catch (ClienteException e) {
            response.put("mensaje", "Error al actualizar el cliente");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(clienteActualizado, HttpStatus.CREATED);
    }

}

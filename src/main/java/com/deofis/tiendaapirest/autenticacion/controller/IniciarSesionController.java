package com.deofis.tiendaapirest.autenticacion.controller;

import com.deofis.tiendaapirest.autenticacion.dto.AuthResponse;
import com.deofis.tiendaapirest.autenticacion.dto.IniciarSesionRequest;
import com.deofis.tiendaapirest.autenticacion.exception.TokenException;
import com.deofis.tiendaapirest.autenticacion.service.AutenticacionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class IniciarSesionController {

    private final AutenticacionService autenticacionService;

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody IniciarSesionRequest iniciarSesionRequest) {
        Map<String, Object> response = new HashMap<>();
        AuthResponse authResponse;

        try {
            authResponse = this.autenticacionService.iniciarSesion(iniciarSesionRequest);
        } catch (TokenException e) {
            response.put("mensaje", "Error al iniciar sesión");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}

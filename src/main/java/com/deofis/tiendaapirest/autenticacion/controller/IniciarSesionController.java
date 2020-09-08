package com.deofis.tiendaapirest.autenticacion.controller;

import com.deofis.tiendaapirest.autenticacion.dto.AuthResponse;
import com.deofis.tiendaapirest.autenticacion.dto.IniciarSesionRequest;
import com.deofis.tiendaapirest.autenticacion.dto.RefreshTokenRequest;
import com.deofis.tiendaapirest.autenticacion.exception.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.exception.TokenException;
import com.deofis.tiendaapirest.autenticacion.service.AutenticacionService;
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

/**
 * API que maneja el inicio, extensión y cierre de sesión de los usuarios.
 */

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class IniciarSesionController {

    private final AutenticacionService autenticacionService;

    /**
     * Iniciar sesión en la App.
     * URL: ~/api/auth/login
     * HttpMethod: POST
     * HttpStatus: OK
     * @param iniciarSesionRequest IniciarSesionRequest con las credenciales del usuario.
     * @return ResponseEntity con los datos de autenticacón (JWT, email del usuario, token
     * para extender sesión, tiempo de expiración del jwt).
     */
    @PostMapping("/login")
    public ResponseEntity<?> signin(@Valid @RequestBody IniciarSesionRequest iniciarSesionRequest) {
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

    /**
     * Extender la sesión generando un nuevo JWT.
     * URL: ~/api/auth/refresh/token
     * HttpMethod: POST
     * HttpStatus: OK
     * @param refreshTokenRequest RefreshTokenRequest con los datos: refreshToken y email del
     *                            usuario.
     * @return ResponseEntity AuthResponse con el nuevo JWT.
     */
    @PostMapping("/refresh/token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        Map<String, Object> response = new HashMap<>();
        AuthResponse authResponse;

        try {
            authResponse = this.autenticacionService.refrescarToken(refreshTokenRequest);
        } catch (AutenticacionException e) {
            response.put("mensaje", "Error al refrescar token y extender sesión");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    /**
     * Cerrar sesión en la App.
     * @param refreshTokenRequest RefreshTokenRequest con el refreshToken y el email del
     *                            usuario.
     * @return ResponseEntity String con mensaje de que se cerró sesión con éxito.
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            this.autenticacionService.cerrarSesion(refreshTokenRequest);
        } catch (AutenticacionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Sesión cerrada con éxito", HttpStatus.OK);
    }

}

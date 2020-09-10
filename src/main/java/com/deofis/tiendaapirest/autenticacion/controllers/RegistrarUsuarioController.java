package com.deofis.tiendaapirest.autenticacion.controllers;

import com.deofis.tiendaapirest.autenticacion.dto.SignupRequest;
import com.deofis.tiendaapirest.autenticacion.exceptions.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.exceptions.PasswordException;
import com.deofis.tiendaapirest.autenticacion.services.AutenticacionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This API works for register a new user into the App.
 */

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class RegistrarUsuarioController {

    private final AutenticacionService autenticacionService;

    /**
     * Takes a request from the client with the data: username, password and email, creates
     * the new user, and send verification email to activate the account.
     * URL: ~/api/auth/signup
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param signupRequest with user credentials.
     * @return String with success/error message.
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            this.autenticacionService.registrarse(signupRequest);
            return new ResponseEntity<>("Usuario registrado exitosamente." +
                    "Comprueba tu correo para activar tu cuenta.", HttpStatus.CREATED);
        } catch (AutenticacionException | PasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Verifica una cuenta y marca al usuario como enabled (Habilitado para usar la cuenta).
     * URL: ~/api/auth/accountVerification/aaaa-bbbb-1111-2222
     * HttpMethod: GET
     * HttpStatus: OK
     * @param token String token de activación.
     * @return ResponseEntity String mensaje de éxito/error.
     */
    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String token) {

        try {
            this.autenticacionService.verificarCuenta(token);
            return new ResponseEntity<>("Cuenta verificada exitosamente", HttpStatus.OK);
        } catch (AutenticacionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

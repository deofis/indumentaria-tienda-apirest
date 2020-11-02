package com.deofis.tiendaapirest.autenticacion.controllers;

import com.deofis.tiendaapirest.autenticacion.dto.SignupRequest;
import com.deofis.tiendaapirest.autenticacion.exceptions.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.exceptions.PasswordException;
import com.deofis.tiendaapirest.autenticacion.services.AutenticacionService;
import com.deofis.tiendaapirest.clientes.exceptions.ClienteException;
import com.deofis.tiendaapirest.perfiles.dto.PerfilDTO;
import com.deofis.tiendaapirest.perfiles.services.PerfilService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This API works for register a new user into the App.
 */

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class RegistrarUsuarioController {

    private final AutenticacionService autenticacionService;
    private final PerfilService perfilService;

    /**
     * Recibe una request para registrar una nueva cuenta de usuario. Crea el usuario correspondiente, crea un nuevo
     * perfil, y asocia los datos del cliente con el mismo.
     * URL: ~/api/auth/signup
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param signupRequest con las credenciales de usuario, y los datos de cliente (Nombre y Apellido).
     * @return ResponseEntity con mensaje de éxito/error y el Perfil nuevo creado.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        PerfilDTO perfil;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("error", "Bad Request");
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            this.autenticacionService.registrarse(signupRequest);
            perfil = this.perfilService.cargarPerfil(signupRequest.getCliente(), signupRequest.getEmail());
        } catch (AutenticacionException | PasswordException | ClienteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Usuario registrado exitosamente. " +
                "Comprueba tu correo para activar tu cuenta.");
        response.put("perfil", perfil);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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

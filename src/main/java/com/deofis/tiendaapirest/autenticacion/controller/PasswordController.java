package com.deofis.tiendaapirest.autenticacion.controller;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.dto.CambiarPasswordRequest;
import com.deofis.tiendaapirest.autenticacion.exception.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.service.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API que se dedica al cambio/reinicio de contraseña de los usuarios del sistema.
 */

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    /**
     * Cambiar contraseña de un usuario ya logueado en la App.
     * URL: ~/api/auth/cambiar-password
     * HttpMethod: POST
     * HttpStatus: OK
     * @param passwordRequest CambiarPasswordRequest tiene los datos de la contraseña.
     *                        Usamos este objeto para no pasar la contraseña por un RequestParam.
     * @return ResponseEntity con un mensaje de éxito/error.
     */
    @PostMapping("/cambiar-password")
    public ResponseEntity<String> prueba(@RequestBody CambiarPasswordRequest passwordRequest) {
        try {
            Usuario usuario = this.passwordService.cambiarPassword(passwordRequest);
            return new ResponseEntity<>("Contraseña cambiada con éxito. Email del usuario: "
                    + usuario.getEmail(), HttpStatus.OK);
        } catch (AutenticacionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

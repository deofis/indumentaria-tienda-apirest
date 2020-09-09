package com.deofis.tiendaapirest.autenticacion.controller;

import com.deofis.tiendaapirest.autenticacion.dto.UsuarioDTO;
import com.deofis.tiendaapirest.autenticacion.exception.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UsuariosController {

    private final UsuarioService usuarioService;

    /**
     * API para listar todos los usuarios registrados en el sistema.
     * URL: ~/api/usuarios
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con la lista de usuarios, o un mensaje con los errores.
     */
    @GetMapping("/usuarios")
    public ResponseEntity<?> listarUsuarios() {
        Map<String, Object> response = new HashMap<>();
        List<UsuarioDTO> usuarios;

        try {
            usuarios = this.usuarioService.listarUsuarios();
        } catch (AutenticacionException e) {
            response.put("mensaje", "Error al obtener el listado de usuarios");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

}

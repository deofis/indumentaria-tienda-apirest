package com.deofis.tiendaapirest.perfiles.controllers;

import com.deofis.tiendaapirest.autenticacion.domain.Rol;
import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.dto.CambioRolRequest;
import com.deofis.tiendaapirest.autenticacion.dto.UsuarioDTO;
import com.deofis.tiendaapirest.autenticacion.exceptions.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.exceptions.PasswordException;
import com.deofis.tiendaapirest.autenticacion.exceptions.RegistrosException;
import com.deofis.tiendaapirest.perfiles.domain.CambioHabilitacionUsuarios;
import com.deofis.tiendaapirest.perfiles.domain.CambioRol;
import com.deofis.tiendaapirest.perfiles.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API's dirigadas al panel de administración de usuarios, que será solo accesible a aquellos
 * usuarios que tengan asignado el rol de administrador: 'ROLE_ADMIN'.
 */

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UsuariosController {

    // ################ IMPORTANTE: SECURIZAR CONTROLADOR PARA ROLE_ADMIN! ###################

    private final UsuarioService usuarioService;

    /**
     * API para listar todos los usuarios registrados en el sistema.
     * URL: ~/api/usuarios
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con la lista de usuarios, o un mensaje con los errores.
     */
    @Secured("ROLE_ADMIN")
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

    /**
     * Lista los roles de usuarios que existen.
     * URL: ~/api/usuarios/roles
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con la lista de roles.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/usuarios/roles")
    public ResponseEntity<?> listarRoles() {
        Map<String, Object> response = new HashMap<>();
        List<Rol> roles;

        try {
            roles = this.usuarioService.listarRoles();
        } catch (AutenticacionException e) {
            response.put("mensaje", "Error al obtener los roles");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    /**
     * Obtiene un usuario específico.
     * URL: ~/api/usuarios/usuario@email.com
     * HttpMethod: GET
     * HttpStatus: OK
     * @param usuarioEmail String email del usuario.
     * @return ResponseEntity con la información del usuario.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/usuarios/{usuarioEmail}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable String usuarioEmail) {
        Map<String, Object> response = new HashMap<>();
        UsuarioDTO usuario;

        try {
            usuario = this.usuarioService.getUsuario(usuarioEmail);
        } catch (AutenticacionException e) {
            response.put("mensaje", "Error al obtener el usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    /**
     * Crea un nuevo usuario desde el panel de administrador. Usuario ya activado, con el rol asignado.
     * URL: ~/api/usuarios
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param usuario Usuario a crear.
     * @return ResponseEntity con los datos no sensibles del usuario creado.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/usuarios/crear")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario) {
        Map<String, Object> response = new HashMap<>();
        UsuarioDTO nuevoUsuario;

        try {
            nuevoUsuario = this.usuarioService.crear(usuario);
        } catch (AutenticacionException | PasswordException e) {
            response.put("mensaje", "Error al crear el nuevo usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    /**
     * Cambia el rol de un usuario y se registra el cambio.
     * URL: ~/api/usuarios/cambiar-rol
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param cambioRolRequest Petición para cambiar el rol con los datos: rol del id nuevo,
     *                         email del usuario a cambiar rol.
     * @return ResponseEntity con el registro del cambio de rol.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/usuarios/cambiar-rol")
    public ResponseEntity<?> cambiarRolUsuario(@RequestBody CambioRolRequest cambioRolRequest) {
        Map<String, Object> response = new HashMap<>();
        CambioRol registro;

        try {
            registro = this.usuarioService.cambiarRol(cambioRolRequest);
        } catch (AutenticacionException | RegistrosException e) {
            response.put("mensaje", "Error al cambiar el rol del usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    /**
     *
     * Como administrador, deshabilitar la cuenta de un usuario.
     * URL: ~/api/usuarios/deshabilitar
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param usuarioEmail RequestParam String email del usuario a deshabilitar.
     * @return ResponseEntity con el registro del cambio.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/usuarios/deshabilitar")
    public ResponseEntity<?> deshabilitarUsuario(@RequestParam String usuarioEmail) {
        Map<String, Object> response = new HashMap<>();
        CambioHabilitacionUsuarios regristro;

        try {
            regristro = this.usuarioService.deshabilitar(usuarioEmail);
        } catch (AutenticacionException | RegistrosException e) {
            response.put("mensaje", "Error al deshabilitar usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(regristro, HttpStatus.CREATED);
    }

    /**
     * Como administrador, habilitar la cuenta de un usuario.
     * URL: ~/api/usuarios/habilitar
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param usuarioEmail RequestParam String email del usuario a habilitar.
     * @return ResponseEntity con el registro del cambio.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/usuarios/habilitar")
    public ResponseEntity<?> habilitarUsuario(@RequestParam String usuarioEmail) {
        Map<String, Object> response = new HashMap<>();
        CambioHabilitacionUsuarios regristro;

        try {
            regristro = this.usuarioService.habilitar(usuarioEmail);
        } catch (AutenticacionException | RegistrosException e) {
            response.put("mensaje", "Error al habilitar usuario");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(regristro, HttpStatus.CREATED);
    }

    /**
     * Como administrador, ver los registros de cambios de rol.
     * URL: ~/api/usuarios/registros/cambio-rol
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con el listado de los registros.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/usuarios/registros/cambio-rol")
    public ResponseEntity<?> listarRegistrosCambioRol() {
        Map<String, Object> response = new HashMap<>();
        List<CambioRol> registros;

        try {
            registros = this.usuarioService.listarRegistrosCambioRol();
        } catch (RegistrosException e) {
            response.put("mensaje", "Error al listar los registros de cambio de rol");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (registros.size() == 0) {
            response.put("error", "No existen registros de cambios de rol hasta la fecha");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("registros", registros);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Como administrador, ver los registros de cambios en la habilitación de usuarios.
     * URL: ~/api/usuarios/registros/habilitacion
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con el listado de los registros.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/usuarios/registros/habilitacion")
    public ResponseEntity<?> listarRegistrosCambiosHabilitacionUsuarios() {
        Map<String, Object> response = new HashMap<>();
        List<CambioHabilitacionUsuarios> registros;

        try {
            registros = this.usuarioService.listarRegistrosHabilitacion();
        } catch (RegistrosException e) {
            response.put("mensaje", "Error al listar los registros de habilitacion de usuarios");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (registros.size() == 0) {
            response.put("error", "No existen registros de habilitación de usuarios hasta la fecha");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("registros", registros);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

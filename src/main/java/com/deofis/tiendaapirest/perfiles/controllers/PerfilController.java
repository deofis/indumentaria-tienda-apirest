package com.deofis.tiendaapirest.perfiles.controllers;

import com.deofis.tiendaapirest.autenticacion.exceptions.AutenticacionException;
import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.clientes.exceptions.ClienteException;
import com.deofis.tiendaapirest.perfiles.domain.Carrito;
import com.deofis.tiendaapirest.perfiles.dto.PerfilDTO;
import com.deofis.tiendaapirest.perfiles.exceptions.CarritoException;
import com.deofis.tiendaapirest.perfiles.exceptions.PerfilesException;
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
 * API dirigida a la administración de perfiles, usuarios y sus datos para transacciones dentro
 * del sistema. Rol asignado para manejo de perfiles: 'ROLE_USER'. (*ver: admins tambien pueden, o tienen
 * que crearse un usuario para realizar compras en el sistema?).
 */

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    /**
     * Como usuario quiero cargar mis datos de cliente a mi perfil. (Carga datos a usuario logueado
     * en el contexto).
     * URL:  ~/api/perfiles/cargar-cliente
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param cliente Cliente a cargar.
     * @return ResponseEntity Perfil con los datos del cliente.
     */
    @PostMapping("/perfiles/cargar-cliente")
    public ResponseEntity<?> cargarDatos(@Valid @RequestBody Cliente cliente, BindingResult result) {
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
            perfil = this.perfilService.cargarPerfil(cliente);
        } catch (ClienteException | AutenticacionException | PerfilesException e) {
            response.put("mensaje", "Error al cargar datos al perfil");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Datos de cliente cargados exitosamente en el perfil");
        response.put("perfil", perfil);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Como usuario, quiero actualizar mis datos de cliente en mi perfil.
     * URL: ~/api/perfiles/actualizar-cliente
     * HttpMethod: PUT
     * HttpStatus: CREATED
     * @param cliente Cliente actualizado.
     * @return ResponseEntity Perfil con los datos del cliente actualizados.
     */
    @PutMapping("/perfiles/actualizar-cliente")
    public ResponseEntity<?> actualizarDatos(@Valid @RequestBody Cliente cliente, BindingResult result) {
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
            perfil = this.perfilService.actualizarPerfil(cliente);
        } catch (ClienteException | AutenticacionException | PerfilesException e) {
            response.put("mensaje", "Error al actualizar datos del cliente");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Datos actualizados con éxito.");
        response.put("perfil", perfil);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtiene los datos del cliente del usuario logueado.
     * URL: ~/api/perfiles/cliente
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity Cliente con los datos del cliente.
     */
    @GetMapping("/perfiles/cliente")
    public ResponseEntity<?> obtenerCliente() {
        Map<String, Object> response = new HashMap<>();
        Cliente cliente;

        try {
            cliente = this.perfilService.obtenerDatosCliente();
        } catch (ClienteException | PerfilesException | AutenticacionException e) {
            response.put("mensaje", "Error al obtener los datos del cliente para el usuario logueado");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("cliente", cliente);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    /**
     * Permite ver el perfil completo del usuario logueado.
     * URL: ~/api/perfiles/ver
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity PerfilDTO datos del perfil.
     */
    @GetMapping("/perfiles/ver")
    public ResponseEntity<?> verPerfil() {
        Map<String, Object> response = new HashMap<>();
        PerfilDTO perfil;

        try {
            perfil = this.perfilService.obtenerPerfil();
        } catch (PerfilesException | AutenticacionException e) {
            response.put("mensaje", "Error al obtener el perfil del usuario logueado");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("perfil", perfil);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Ver el carrito del perfil del usuario actual.
     * URL: ~/api/perfiles/carrito
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity Carrito del perfil.
     */
    @GetMapping("/perfiles/carrito")
    public ResponseEntity<?> verCarrito() {
        Map<String, Object> response = new HashMap<>();
        Carrito carrito;

        try {
            carrito = this.perfilService.obtenerCarrito();
        } catch (PerfilesException | CarritoException e) {
            response.put("mensaje", "Error al obtener el carrito del perfil");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("carrito", carrito);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

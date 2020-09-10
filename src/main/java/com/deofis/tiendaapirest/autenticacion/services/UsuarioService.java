package com.deofis.tiendaapirest.autenticacion.services;

import com.deofis.tiendaapirest.autenticacion.domain.CambioHabilitacionUsuarios;
import com.deofis.tiendaapirest.autenticacion.domain.CambioRol;
import com.deofis.tiendaapirest.autenticacion.domain.Rol;
import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.dto.CambioRolRequest;
import com.deofis.tiendaapirest.autenticacion.dto.UsuarioDTO;

import java.util.List;

/**
 * Servicio para administrar los usuarios registrados en el sistema.
 */

public interface UsuarioService {

    List<Rol> listarRoles();

    /**
     * Lista los usuarios ordenados por fecha de creacon.
     * @return List con los usuarios registrados.
     */
    List<UsuarioDTO> listarUsuarios();

    /**
     * Obtiene un usuario en particular.
     * @param usuarioEmail String email del usuario.
     * @return Datos del usuario.
     */
    UsuarioDTO getUsuario(String usuarioEmail);

    /**
     * Como administrador, permite crear un nuevo usuario ya activado, con el rol especificado.
     * @param usuario Datos del usuario completos (email, pass y el rol).
     * @return UsuarioDTO --> Datos del usuario no sensibles (sin pass).
     */
    UsuarioDTO crear(Usuario usuario);

    /**
     * Como administrador, permite cambiar el rol de un usuario. Registra esta transacción
     * con los datos: usuario que hizo el cambio, usuario al que se le cambio el rol,
     * nuevo rol del usuario y fecha del cambio de rol.
     * @param cambioRolRequest CambioRolRequest con los datos: Long id del rol nuevo, String
     *                         email del usuario.
     * @return CambioRol registro del cambio de rol.
     */
    CambioRol cambiarRol(CambioRolRequest cambioRolRequest);

    CambioHabilitacionUsuarios deshabilitar(String usuarioEmail);

    CambioHabilitacionUsuarios habilitar(String usuarioEmail);
}

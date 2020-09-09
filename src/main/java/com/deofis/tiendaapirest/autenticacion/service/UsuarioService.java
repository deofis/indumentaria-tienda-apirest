package com.deofis.tiendaapirest.autenticacion.service;

import com.deofis.tiendaapirest.autenticacion.dto.UsuarioDTO;

import java.util.List;

/**
 * Servicio para administrar los usuarios registrados en el sistema.
 */

public interface UsuarioService {

    List<UsuarioDTO> listarUsuarios();
}

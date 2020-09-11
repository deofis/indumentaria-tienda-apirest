package com.deofis.tiendaapirest.perfiles.services;

import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.perfiles.dto.PerfilDTO;

public interface PerfilService {

    /**
     * Se encarga de tomar los datos del cliente y asignarlos al usuario logueado.
     * NO actualiza datos de un usuario ya registrado.
     */
    PerfilDTO cargarPerfil(Cliente cliente);

    PerfilDTO actualizarPerfil(Cliente clienteActualizado);
}

package com.deofis.tiendaapirest.perfiles.services;

import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.perfiles.dto.PerfilDTO;

public interface PerfilService {

    /**
     * Se encarga de tomar los datos del cliente y asignarlos al usuario logueado.
     * NO actualiza datos de un usuario ya registrado.
     */
    PerfilDTO cargarPerfil(Cliente cliente);

    /**
     * Actualiza los datos de cliente asociados al perfil del usuario logueado.
     * @param clienteActualizado Cliente datos del cliente actualizado.
     * @return PerfilDTO datos del perfil luego del cambio.
     */
    PerfilDTO actualizarPerfil(Cliente clienteActualizado);

    /**
     * Obtener datos del cliente del usuario logueado.
     * @return Cliente datos del cliente del usuario logueado.
     */
    Cliente obtenerDatosCliente();
}

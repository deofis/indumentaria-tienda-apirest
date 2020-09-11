package com.deofis.tiendaapirest.autenticacion.services;

import com.deofis.tiendaapirest.clientes.domain.Cliente;

public interface PerfilService {

    /**
     * Se encarga de tomar los datos del cliente y asignarlos al usuario logueado.
     * NO actualiza datos de un usuario ya registrado.
     */
    Cliente cargarPerfil(Cliente cliente);

    Cliente actualizarPerfil(Cliente clienteActualizado);
}

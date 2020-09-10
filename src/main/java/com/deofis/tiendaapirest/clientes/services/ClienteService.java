package com.deofis.tiendaapirest.clientes.services;

import com.deofis.tiendaapirest.clientes.domain.Cliente;

public interface ClienteService {

    Cliente obtenerCliente(Long id);

    Cliente crear(Cliente cliente);

    Cliente actualizar(Cliente clienteActualizado, Long clienteId);
}

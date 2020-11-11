package com.deofis.tiendaapirest.clientes.services;

import com.deofis.tiendaapirest.clientes.domain.Cliente;

public interface ClienteService {

    /**
     * Obtiene el cliente requerido.
     * @param id Long id del cliente.
     * @return Cliente
     */
    Cliente obtenerCliente(Long id);

    /**
     * Crea un nuevo cliente y lo guarda en la base de datos.
     * @param cliente Cliente a crear.
     * @return Cliente creado y guardado.
     */
    Cliente crear(Cliente cliente);

    /**
     * Actualiza los datos de un cliente existente.
     * @param clienteActualizado Cliente con los datos actualizados.
     * @param clienteId Long id del cliente guardado a actualizar.
     * @return Cliente con los datos actualizados.
     */
    Cliente actualizar(Cliente clienteActualizado, Long clienteId);
}

package com.deofis.tiendaapirest.clientes.services;

import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.clientes.exceptions.ClienteException;
import com.deofis.tiendaapirest.clientes.repositories.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    @Override
    public Cliente obtenerCliente(Long id) {
        return this.clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException("No existe el cliente con id: " + id));
    }

    @Transactional
    @Override
    public Cliente crear(Cliente cliente) {
        Cliente nuevoCliente = Cliente.builder()
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .telefono(cliente.getTelefono())
                .email(cliente.getEmail())
                .direccion(cliente.getDireccion())
                .build();

        return this.clienteRepository.save(nuevoCliente);
    }

    @Transactional
    @Override
    public Cliente actualizar(Cliente clienteActualizado, Long clienteId) {
        Cliente clienteActual = this.obtenerCliente(clienteId);

        clienteActual.setNombre(clienteActualizado.getNombre());
        clienteActual.setApellido(clienteActualizado.getApellido());
        clienteActual.setTelefono(clienteActualizado.getTelefono());
        clienteActual.setDireccion(clienteActualizado.getDireccion());
        /*
        clienteActual.setCodigoPostal(clienteActualizado.getCodigoPostal());
        clienteActual.setCalle(clienteActualizado.getCalle());
        clienteActual.setNumeroCalle(clienteActualizado.getNumeroCalle());
        clienteActual.setPiso(clienteActualizado.getPiso());
        clienteActual.setDepartamento(clienteActualizado.getDepartamento());

         */

        return this.clienteRepository.save(clienteActual);
    }
}

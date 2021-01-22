package com.deofis.tiendaapirest.clientes.services;

import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.clientes.domain.Direccion;
import com.deofis.tiendaapirest.clientes.exceptions.ClienteException;
import com.deofis.tiendaapirest.clientes.repositories.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente obtenerCliente(Long id) {
        return this.findById(id);
    }

    @Override
    public Cliente crear(Cliente cliente) {
        Cliente nuevoCliente = Cliente.builder()
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .telefono(cliente.getTelefono())
                .email(cliente.getEmail())
                .direccion(cliente.getDireccion())
                .build();

        return this.save(nuevoCliente);
    }

    @Override
    public Cliente actualizar(Cliente clienteActualizado, Long clienteId) {
        Cliente clienteActual = this.obtenerCliente(clienteId);

        clienteActual.setNombre(clienteActualizado.getNombre());
        clienteActual.setApellido(clienteActualizado.getApellido());
        clienteActual.setTelefono(clienteActualizado.getTelefono());
        clienteActual.setDireccion(clienteActualizado.getDireccion());

        return this.save(clienteActual);
    }

    @Override
    public Cliente actualizarDireccion(Cliente cliente, Direccion direccion) {
        cliente.setDireccion(direccion);
        return this.save(cliente);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findById(Long aLong) {
        return this.clienteRepository.findById(aLong)
                .orElseThrow(() -> new ClienteException("No existe el cliente con id: " + aLong));
    }

    @Transactional
    @Override
    public Cliente save(Cliente object) {
        return this.clienteRepository.save(object);
    }

    @Override
    public void delete(Cliente object) {
        System.out.println("Not implemented...");
    }

    @Override
    public void deleteById(Long aLong) {
        System.out.println("Not implemented...");
    }
}

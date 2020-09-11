package com.deofis.tiendaapirest.autenticacion.services;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.exceptions.PerfilesException;
import com.deofis.tiendaapirest.autenticacion.repositories.UsuarioRepository;
import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.clientes.services.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PerfilServiceImpl implements PerfilService {

    private final ClienteService clienteService;
    private final AutenticacionService autenticacionService;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public Cliente cargarPerfil(Cliente cliente) {
        Usuario usuario = this.autenticacionService.getUsuarioActual();

        if (usuario.getCliente() != null) {
            throw new PerfilesException("El usuario ya tiene asignado datos de un cliente.");
        }

        Cliente clienteCargado = this.clienteService.crear(cliente);

        usuario.setCliente(clienteCargado);
        this.usuarioRepository.save(usuario);

        return clienteCargado;
    }

    @Transactional
    @Override
    public Cliente actualizarPerfil(Cliente clienteActualizado) {
        Usuario usuario = this.autenticacionService.getUsuarioActual();

        Cliente clienteActual = this.clienteService.obtenerCliente(usuario.getCliente().getId());

        return this.clienteService.actualizar(clienteActualizado, clienteActual.getId());
    }
}

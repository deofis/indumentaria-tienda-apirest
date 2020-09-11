package com.deofis.tiendaapirest.perfiles.services;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.services.AutenticacionService;
import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.clientes.services.ClienteService;
import com.deofis.tiendaapirest.perfiles.domain.Perfil;
import com.deofis.tiendaapirest.perfiles.dto.PerfilDTO;
import com.deofis.tiendaapirest.perfiles.exceptions.PerfilesException;
import com.deofis.tiendaapirest.perfiles.repositories.PerfilRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class PerfilServiceImpl implements PerfilService {

    private final PerfilRepository perfilRepository;
    private final ClienteService clienteService;
    private final AutenticacionService autenticacionService;

    @Transactional
    @Override
    public PerfilDTO cargarPerfil(Cliente cliente) {
        Usuario usuario = this.autenticacionService.getUsuarioActual();
        Cliente clienteCargado = this.clienteService.crear(cliente);

        Perfil perfil = Perfil.builder()
                .usuario(usuario)
                .cliente(clienteCargado)
                .build();

        try {
            return this.mapToDTO(this.perfilRepository.save(perfil));
        } catch (DataIntegrityViolationException e) {
            throw new PerfilesException("El usuario ya tiene asignado datos de cliente a su perfil");
        }
        /*
        if (usuario.getCliente() != null) {
            throw new PerfilesException("El usuario ya tiene asignado datos de un cliente.");
        }
        usuario.setCliente(clienteCargado);
        this.usuarioRepository.save(usuario);
         */
    }

    @Transactional
    @Override
    public PerfilDTO actualizarPerfil(Cliente clienteActualizado) {
        Usuario usuario = this.autenticacionService.getUsuarioActual();

        Perfil perfil = this.perfilRepository.findByUsuario(usuario)
                .orElseThrow(() -> new PerfilesException("No existe un perfil para el usuario: " +
                        usuario.getEmail()));


        Cliente cliente = this.clienteService.obtenerCliente(perfil.getCliente().getId());
        perfil.setCliente(this.clienteService.actualizar(clienteActualizado, cliente.getId()));

        return this.mapToDTO(this.perfilRepository.save(perfil));
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente obtenerDatosCliente() {
        Usuario usuario = this.autenticacionService.getUsuarioActual();
        Perfil perfil = this.perfilRepository.findByUsuario(usuario)
                .orElseThrow(() -> new PerfilesException("No existe el perfil para el usuario."));


        return this.clienteService.obtenerCliente(perfil.getCliente().getId());
    }

    private PerfilDTO mapToDTO(Perfil perfil) {
        return PerfilDTO.builder()
                .usuario(perfil.getUsuario().getEmail())
                .cliente(perfil.getCliente())
                .build();
    }
}

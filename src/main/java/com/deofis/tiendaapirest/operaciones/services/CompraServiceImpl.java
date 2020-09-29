package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.clientes.exceptions.ClienteException;
import com.deofis.tiendaapirest.clientes.repositories.ClienteRepository;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.exceptions.OperacionException;
import com.deofis.tiendaapirest.operaciones.repositories.OperacionRepository;
import com.deofis.tiendaapirest.perfiles.services.PerfilService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompraServiceImpl implements CompraService {

    private final OperacionRepository operacionRepository;
    private final ClienteRepository clienteRepository;
    private final PerfilService perfilService;

    @Override
    public List<Operacion> historialCompras() {
        Long clienteId = this.perfilService.obtenerPerfil().getCliente().getId();
        Cliente cliente = this.clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteException("No existe el cliente con id: " + clienteId));


        return this.operacionRepository.findAllByClienteOrderByFechaOperacionAsc(cliente);
    }

    @Override
    public Operacion verCompra(Long nroOperacion) {
        Long clienteId = this.perfilService.obtenerPerfil().getCliente().getId();
        Cliente cliente = this.clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteException("No existe el cliente con id: " + clienteId));

        return this.operacionRepository.findByNroOperacionAndCliente(nroOperacion, cliente)
                .orElseThrow(() -> new OperacionException("No existe la operación del cliente seleccionado"));
    }
}

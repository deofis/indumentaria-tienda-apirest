package com.deofis.tiendaapirest.clientes.services;

import com.deofis.tiendaapirest.clientes.domain.Ciudad;
import com.deofis.tiendaapirest.clientes.domain.Estado;
import com.deofis.tiendaapirest.clientes.domain.Pais;
import com.deofis.tiendaapirest.clientes.exceptions.PaisesException;
import com.deofis.tiendaapirest.clientes.repositories.EstadoRepository;
import com.deofis.tiendaapirest.clientes.repositories.PaisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocalizacionServiceImpl implements LocalizacionService {

    private final PaisRepository paisRepository;
    private final EstadoRepository estadoRepository;

    @Override
    public List<Pais> listarPaises() {
        return this.paisRepository.findAllByOrderByNombreAsc();
    }

    @Override
    public Pais obtenerPais(Long id) {
        return this.paisRepository.findById(id)
                .orElseThrow(() -> new PaisesException("País no encontrado con id: " + id));
    }

    @Override
    public Pais obtenerPais(String nombrePais) {
        return this.paisRepository.findByNombre(nombrePais)
                .orElseThrow(() -> new PaisesException("País no encontrado con nombre: " + nombrePais));
    }

    @Override
    public List<Estado> estadosDePais(String nombrePais) {
        Pais pais = this.obtenerPais(nombrePais);

        return pais.getEstados();
    }

    @Override
    public List<Ciudad> ciudadesEstado(String pais, String estado) {
        return null;
    }
}

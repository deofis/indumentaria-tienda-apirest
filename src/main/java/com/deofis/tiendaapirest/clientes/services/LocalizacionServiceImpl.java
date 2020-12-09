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
    public List<Estado> estadosDePais(Long paisId) {
        Pais pais = this.obtenerPais(paisId);

        return pais.getEstados();
    }

    @Override
    public List<Estado> estadosDePais(String nombrePais) {
        Pais pais = this.obtenerPais(nombrePais);

        return pais.getEstados();
    }

    @Override
    public Estado obtenerEstado(String nombreEstado) {
        return this.estadoRepository.findByNombre(nombreEstado)
                .orElseThrow(() -> new PaisesException("Estado no encontrado con nombre: " + nombreEstado));
    }

    @Override
    public Estado obtenerEstado(Long estadoId) {
        return this.estadoRepository.findById(estadoId)
                .orElseThrow(() -> new PaisesException("Estado no encontrado con id: " + estadoId));
    }

    @Override
    public List<Ciudad> ciudadesEstado(String nombrePais, String nombreEstado) {
        Pais pais = this.obtenerPais(nombrePais);
        Estado estado = null;

        for (Estado state: pais.getEstados()) {
            if (state.getNombre().equals(nombreEstado)) estado = state;
        }

        if (estado == null) throw new PaisesException("El estado: " + nombreEstado +
                " no pertenece al país: " + nombreEstado);


        return estado.getCiudades();
    }

    @Override
    public List<Ciudad> ciudadesEstado(Long paisId, Long estadoId) {
        Pais pais = this.obtenerPais(paisId);
        Estado estado = null;

        for (Estado state: pais.getEstados()) {
            if (state.getId().equals(estadoId)) estado = state;
        }

        if (estado == null) throw new PaisesException("El estado: " + estadoId +
                " no pertenece al país: " + paisId);


        return estado.getCiudades();
    }
}

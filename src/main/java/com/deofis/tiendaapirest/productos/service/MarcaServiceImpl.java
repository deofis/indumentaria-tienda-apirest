package com.deofis.tiendaapirest.productos.service;

import com.deofis.tiendaapirest.productos.domain.Marca;
import com.deofis.tiendaapirest.productos.exception.ProductoException;
import com.deofis.tiendaapirest.productos.repository.MarcaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;

    @Override
    @Transactional
    public Marca crear(Marca marca) {
        Marca marcaNueva = Marca.builder()
                .nombre(marca.getNombre())
                .build();

        return this.marcaRepository.save(marcaNueva);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Marca> obtenerMarcas() {
        return this.marcaRepository.findAllByOrderByNombreAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Marca obtenerMarca(Long id) {
        return this.marcaRepository.findById(id)
                .orElseThrow(() -> new ProductoException("Marca no existente con id: " + id));
    }

    @Override
    @Transactional
    public Marca actualizar(Marca marca, Long id) {
        Marca marcaActual = this.obtenerMarca(id);

        marcaActual.setNombre(marca.getNombre());
        return this.marcaRepository.save(marcaActual);
    }
}

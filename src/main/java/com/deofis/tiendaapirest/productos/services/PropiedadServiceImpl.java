package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Propiedad;
import com.deofis.tiendaapirest.productos.domain.ValorPropiedad;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.PropiedadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropiedadServiceImpl implements PropiedadService {

    private final PropiedadRepository propiedadRepository;

    @Override
    public Propiedad obtenerPropiedad(Long propiedadId) {
        return this.propiedadRepository.findById(propiedadId)
                .orElseThrow(() -> new ProductoException("No existe la propiedad con id: " + propiedadId));
    }

    @Override
    public Propiedad crearPropiedad(Propiedad propiedad) {
        return this.propiedadRepository.save(Propiedad.builder()
                .nombre(propiedad.getNombre())
                .valores(propiedad.getValores())
                .build());
    }

    @Override
    public Propiedad agregarValor(Long propiedadId, ValorPropiedad valorPropiedad) {
        Propiedad propiedad = this.obtenerPropiedad(propiedadId);

        ValorPropiedad nuevoValor = ValorPropiedad.builder()
                .valor(valorPropiedad.getValor())
                .build();

        propiedad.getValores().add(nuevoValor);

        return this.propiedadRepository.save(propiedad);
    }
}

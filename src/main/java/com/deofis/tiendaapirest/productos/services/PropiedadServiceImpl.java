package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.ValorPropiedadProducto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.PropiedadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropiedadServiceImpl implements PropiedadService {

    private final PropiedadRepository propiedadRepository;

    @Override
    public PropiedadProducto obtenerPropiedad(Long propiedadId) {
        return this.propiedadRepository.findById(propiedadId)
                .orElseThrow(() -> new ProductoException("No existe la propiedad con id: " + propiedadId));
    }

    @Override
    public PropiedadProducto crearPropiedad(PropiedadProducto propiedadProducto) {
        return this.propiedadRepository.save(PropiedadProducto.builder()
                .nombre(propiedadProducto.getNombre())
                .valores(propiedadProducto.getValores())
                .build());
    }

    @Override
    public PropiedadProducto agregarValor(Long propiedadId, ValorPropiedadProducto valorPropiedadProducto) {
        PropiedadProducto propiedadProducto = this.obtenerPropiedad(propiedadId);

        ValorPropiedadProducto nuevoValor = ValorPropiedadProducto.builder()
                .valor(valorPropiedadProducto.getValor())
                .build();

        propiedadProducto.getValores().add(nuevoValor);

        return this.propiedadRepository.save(propiedadProducto);
    }
}

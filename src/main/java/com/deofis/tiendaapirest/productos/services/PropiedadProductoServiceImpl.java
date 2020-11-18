package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.PropiedadProducto;
import com.deofis.tiendaapirest.productos.domain.ValorPropiedadProducto;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import com.deofis.tiendaapirest.productos.repositories.PropiedadProductoRepository;
import com.deofis.tiendaapirest.productos.repositories.ValorPropiedadProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PropiedadProductoServiceImpl implements PropiedadProductoService {

    private final PropiedadProductoRepository propiedadProductoRepository;
    private final ValorPropiedadProductoRepository valorPropiedadProductoRepository;

    @Transactional
    @Override
    public PropiedadProducto crearPropiedadProducto(PropiedadProducto propiedadProducto) {
        List<ValorPropiedadProducto> valores = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(propiedadProducto.getValores())) valores.addAll(propiedadProducto.getValores());

        return this.propiedadProductoRepository.save(PropiedadProducto.builder()
                .nombre(propiedadProducto.getNombre())
                .variable(propiedadProducto.isVariable())
                .valores(valores).build());
    }

    @Transactional(readOnly = true)
    @Override
    public List<PropiedadProducto> obtenerPropiedadesProducto() {
        return this.propiedadProductoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public PropiedadProducto obtenerPropiedadProducto(Long propiedadId) {
        return this.propiedadProductoRepository.findById(propiedadId)
                .orElseThrow(() -> new ProductoException("No se encontró la propiedad con id: " + propiedadId));
    }

    @Transactional
    @Override
    public PropiedadProducto actualizarPropiedadProducto(Long propiedadId, PropiedadProducto propiedadProducto) {
        PropiedadProducto propiedadActual = this.obtenerPropiedadProducto(propiedadId);

        propiedadActual.setNombre(propiedadProducto.getNombre());
        propiedadActual.setVariable(propiedadProducto.isVariable());
        return this.propiedadProductoRepository.save(propiedadActual);
    }

    @Transactional
    @Override
    public void eliminarPropiedadProducto(Long propiedadId) {
        this.propiedadProductoRepository.deleteById(propiedadId);
    }

    @Transactional
    @Override
    public PropiedadProducto crearValorPropiedad(Long propiedadId, ValorPropiedadProducto valor) {
        PropiedadProducto propiedad = this.obtenerPropiedadProducto(propiedadId);

        ValorPropiedadProducto nuevoValor = ValorPropiedadProducto.builder()
                .valor(valor.getValor()).build();

        propiedad.getValores().add(nuevoValor);
        return this.propiedadProductoRepository.save(propiedad);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ValorPropiedadProducto> obtenerValoresDePropiedad(Long propiedadId) {
        PropiedadProducto propiedad = this.obtenerPropiedadProducto(propiedadId);

        return propiedad.getValores();
    }

    @Transactional
    @Override
    public PropiedadProducto actualizarValorPropiedad(Long propiedadId, Long valorId, ValorPropiedadProducto valor) {
        PropiedadProducto propiedad = this.obtenerPropiedadProducto(propiedadId);

        boolean existeValor = false;
        for (ValorPropiedadProducto valorPropiedadProducto: propiedad.getValores()) {
            if (valorPropiedadProducto.getId().equals(valorId)) {
                valorPropiedadProducto.setValor(valor.getValor());
                existeValor = true;
                break;
            }
        }

        if (!existeValor) throw new ProductoException("No existe el valor: '".concat(valor.getValor())
                .concat("' para la propiedad: '".concat(propiedad.getNombre())));

        return this.propiedadProductoRepository.save(propiedad);
    }

    @Transactional
    @Override
    public void eliminarValorPropiedad(Long propiedadId, Long valorId) {
        PropiedadProducto propiedad = this.obtenerPropiedadProducto(propiedadId);

        boolean existeValor = false;
        for (ValorPropiedadProducto valorPropiedadProducto: propiedad.getValores()) {
            if (valorPropiedadProducto.getId().equals(valorId)) {
                this.valorPropiedadProductoRepository.deleteById(valorId);
                existeValor = true;
                break;
            }
        }

        if (!existeValor) throw new ProductoException("No existe el valor con id: '"
                .concat(String.valueOf(valorId)).concat("' para la propiedad: '".concat(propiedad.getNombre())));
    }
}

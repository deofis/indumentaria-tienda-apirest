package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.exceptions.OperacionException;
import com.deofis.tiendaapirest.operaciones.repositories.OperacionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final OperacionRepository operacionRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Operacion> listarVentas() {
        return this.operacionRepository.findAllByOrderByFechaOperacionDesc();
    }

    @Transactional(readOnly = true)
    @Override
    public Operacion obtenerVenta(Long nroOperacion) {
        return this.operacionRepository.findById(nroOperacion)
                .orElseThrow(() -> new OperacionException("No existe la operación con id: " + nroOperacion));
    }
}

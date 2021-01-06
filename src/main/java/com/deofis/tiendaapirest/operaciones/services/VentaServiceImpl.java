package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.operaciones.domain.EstadoOperacion;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.exceptions.OperacionException;
import com.deofis.tiendaapirest.operaciones.repositories.OperacionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
    public List<Operacion> ventasPendientePago() {
        List<Operacion> operaciones = this.operacionRepository.findAll();
        List<Operacion> ventasPendientePago = new ArrayList<>();

        for (Operacion op: operaciones) {
            if (op.getEstado().equals(EstadoOperacion.PAYMENT_PENDING))
                ventasPendientePago.add(op);
        }

        return ventasPendientePago;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Operacion> ventasCompletadoPago() {
        List<Operacion> operaciones = this.operacionRepository.findAll();
        List<Operacion> ventasPagoCompletado = new ArrayList<>();

        for (Operacion op: operaciones) {
            if (op.getEstado().equals(EstadoOperacion.PAYMENT_DONE))
                ventasPagoCompletado.add(op);
        }

        return ventasPagoCompletado;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Operacion> ventasEnviadas() {
        List<Operacion> operaciones = this.operacionRepository.findAll();
        List<Operacion> ventasEnviadas = new ArrayList<>();

        for (Operacion op : operaciones) {
            if (op.getEstado().equals(EstadoOperacion.SENT))
                ventasEnviadas.add(op);
        }

        return ventasEnviadas;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Operacion> ventasRecibidas() {
        List<Operacion> operaciones = this.operacionRepository.findAll();
        List<Operacion> ventasRecibidas = new ArrayList<>();

        for (Operacion op: operaciones) {
            if (op.getEstado().equals(EstadoOperacion.RECEIVED))
                ventasRecibidas.add(op);
        }

        return ventasRecibidas;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Operacion> ventasCanceladas() {
        List<Operacion> operaciones = this.operacionRepository.findAll();
        List<Operacion> ventasCanceladas = new ArrayList<>();

        for (Operacion op: operaciones) {
            if (op.getEstado().equals(EstadoOperacion.CANCELLED))
                ventasCanceladas.add(op);
        }

        return ventasCanceladas;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Operacion> ventasFecha(Date fechaDesde, Date fechaHasta) {
        List<Operacion> operaciones = this.operacionRepository.findAll();
        List<Operacion> ventasFecha = new ArrayList<>();

        for (Operacion op: operaciones) {
            if (op.getFechaOperacion().after(fechaDesde) && op.getFechaOperacion().before(fechaHasta)) {
                ventasFecha.add(op);
            }
        }
        return ventasFecha;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Operacion> ventasFechaYEstado(String estado, Date fechaDesde, Date fechaHasta) {
        List<Operacion> ventasFecha = this.operacionRepository.findAllByFechaOperacionBetween(fechaDesde, fechaHasta);
        List<Operacion> ventasTotales = new ArrayList<>();

        for (Operacion op: ventasFecha) {
            if (estado.equalsIgnoreCase(String.valueOf(op.getEstado()))) {
                ventasTotales.add(op);
            }
        }

        return ventasTotales;
    }

    @Transactional(readOnly = true)
    @Override
    public Operacion obtenerVenta(Long nroOperacion) {
        return this.operacionRepository.findById(nroOperacion)
                .orElseThrow(() -> new OperacionException("No existe la operación con id: " + nroOperacion));
    }
}

package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.repositories.OperacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ActiveProfiles("dev")
@Slf4j
class VentaServiceImplTest {

    @Autowired
    VentaService ventaService;

    @Autowired
    OperacionRepository operacionRepository;

    @BeforeEach
    void setUp() {

    }

    @Transactional
    @Test
    void ventasPendientePago() {
        List<Operacion> ventasPendientePago = ventaService.ventasPendientePago();

        log.info("Ventas con pago pendiente --> " + ventasPendientePago);
    }

    @Transactional
    @Test
    void ventasCompletadoPago() {
        List<Operacion> ventasPagoCompletado = ventaService.ventasCompletadoPago();

        log.info("Ventas con pago completado --> " + ventasPagoCompletado);
    }
}
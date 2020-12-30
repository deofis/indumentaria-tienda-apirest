package com.deofis.tiendaapirest.reportes.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@SpringBootTest
@ActiveProfiles("dev")
@Slf4j
class ReporteVentasServiceImplTest {

    @Autowired
    ReporteVentasService reporteVentasService;

    @Transactional
    @Test
    void generarReporteVentasSku() {
        Map<String, Object> reporteVentas = reporteVentasService.generarReporteVentasSku(1L);

        log.info(String.valueOf(reporteVentas));
    }
}
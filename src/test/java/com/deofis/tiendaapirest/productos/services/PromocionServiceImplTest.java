package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.Promocion;
import com.deofis.tiendaapirest.productos.exceptions.ProductoException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
@ActiveProfiles("dev")
@Slf4j
class PromocionServiceImplTest {

    @Autowired
    PromocionService promocionService;

    @Autowired
    ProductoService productoService;

    Producto producto;
    Promocion promocionFija;
    Promocion promocionPorcentaje;

    @BeforeEach
    void setUp() {
        producto = productoService.obtenerProducto(1L);
        promocionFija = Promocion.builder()
                .fechaDesde(new Date())
                .fechaHasta(new Date(new Date().getTime() + 900000))
                .precioOferta(23999.99).build();
        promocionPorcentaje = Promocion.builder()
                .fechaDesde(new Date())
                .fechaHasta(new Date(new Date().getTime() + 900000))
                .porcentaje(.30).build();
    }

    @Transactional
    @Test
    void programarOfertaProducto() {
        log.info("Producto sin promo --> ".concat(String.valueOf(producto)));
        Producto productoConPromo = null;

        try {
            productoConPromo = promocionService.programarOfertaProducto(producto.getId(), promocionFija);
        } catch (ProductoException e) {
            log.warn(e.getMessage());
        }

        log.info("Producto con promo --> ".concat(String.valueOf(productoConPromo)));
    }

    @Transactional
    @Test
    void programarOfertaProductosSubcategoria() {
        log.info("Productos sin promocionar --> " + productoService.obtenerProductos());

        Integer cantidadProductosPromocionados = promocionService.programarOfertaProductosSubcategoria(1L, promocionPorcentaje);

        log.info("Cantidad de productos YA promocionados: " + cantidadProductosPromocionados);
        log.info("Productos promocionados --> " + productoService.obtenerProductos());
    }
}
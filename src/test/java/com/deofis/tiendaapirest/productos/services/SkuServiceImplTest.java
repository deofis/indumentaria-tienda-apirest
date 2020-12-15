package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.domain.Sku;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import com.deofis.tiendaapirest.productos.repositories.ValorPropiedadProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("dev")
@Slf4j
class SkuServiceImplTest {

    @Autowired
    ValorPropiedadProductoRepository valorPropiedadProductoRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    SkuService skuService;

    Producto producto;

    Sku sku;

    @BeforeEach
    void setUp() {
        producto = productoRepository.getOne(1L);

        sku = Sku.builder()
                .precio(20000.00)
                .disponibilidad(13)
                .valores(new ArrayList<>()).build();

        sku.getValores().add(valorPropiedadProductoRepository.getOne(3L));
    }

    @Transactional
    @Test
    void generarSkusProducto() {
        Map<String, Object> map = skuService.generarSkusProducto(producto);
        log.info("Map -> " + map);
    }

    @Transactional
    @Test
    void crearNuevoSku() {
        Sku nuevoSku = skuService.crearNuevoSku(sku, producto);
        log.info(String.valueOf(nuevoSku));
    }

    @Transactional
    @Test
    void actualizarPrecioSku() {
        Sku skuActualizado = skuService.actualizarPrecio(1L, 1.00);
        log.info("Sku Actualizado: " + skuActualizado);
    }

    @Transactional
    @Test
    void actualizarDisponibilidadSku() {
        log.info("Sku: " + skuService.obtenerSku(1L));
        Sku skuActualizado = skuService.actualizarDisponibilidad(1L, 1);
        log.info("Sku Actualizado: " + skuActualizado);
    }
}
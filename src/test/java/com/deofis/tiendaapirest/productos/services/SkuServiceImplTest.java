package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;
import com.deofis.tiendaapirest.productos.repositories.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@SpringBootTest
@Slf4j
class SkuServiceImplTest {
    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    SkuService skuService;

    Producto producto;

    @BeforeEach
    void setUp() {
        producto = productoRepository.getOne(1L);
    }

    @Transactional
    @Test
    void generarSkusProducto() {
        Map<String, Object> map = skuService.generarSkusProducto(producto);
        log.info("Map -> " + map);
    }
}
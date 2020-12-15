package com.deofis.tiendaapirest.productos.services;

import com.deofis.tiendaapirest.productos.domain.Producto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("dev")
@Slf4j
class BuscadorProductosServiceImplTest {

    @Autowired
    BuscadorProductosService buscadorProductosService;

    String termino;
    List<Producto> productoList;

    @BeforeEach
    void setUp() {
        termino = "S10";
        productoList = new ArrayList<>();
    }

    @Transactional
    @Test
    void buscarProductos() {
        productoList = buscadorProductosService.buscarProductos(termino);
        log.info("Lista de productos --> ".concat(String.valueOf(productoList)));
        log.info("Tamaño de la lista --> ".concat(String.valueOf(productoList.size())));
    }
}
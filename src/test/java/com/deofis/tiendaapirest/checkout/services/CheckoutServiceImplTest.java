package com.deofis.tiendaapirest.checkout.services;

import com.deofis.tiendaapirest.operaciones.services.OperacionService;
import com.deofis.tiendaapirest.pagos.factory.OperacionPagoInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("dev")
@Slf4j
class CheckoutServiceImplTest {

    @Autowired
    CheckoutService checkoutService;

    @Autowired
    OperacionService operacionService;

    @BeforeEach
    void setUp() {

    }

    @Transactional
    @Test
    void ejecutarCheckout() {
        OperacionPagoInfo pagoInfo = checkoutService.ejecutarCheckout(1L);

        log.info("ID: " + pagoInfo.getId() + ", STATUS: " + pagoInfo.getStatus() + ", TOTAL: " + pagoInfo.getAmount().getTotalBruto());

        log.info("Pago guardado -->" + operacionService.findById(1L).getPago());
    }
}
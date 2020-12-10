package com.deofis.tiendaapirest.operaciones.controllers;

import com.deofis.tiendaapirest.operaciones.exceptions.OperacionException;
import com.deofis.tiendaapirest.pagos.dto.PaymentPayload;
import com.deofis.tiendaapirest.pagos.services.paypal.PayPalStrategy;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CompletarPagoPayPalController {

    private final PayPalStrategy payPalStrategy;

    /**
     * Completa el pago de una operación por PayPal a traves del token enviado por la API de paypal, que es
     * orderId.
     * URL: ~/api/operaciones/paypal/completar/pago
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param orderId String del id de orden enviado por la API de PayPal (llamado token por ellos)
     * @return ResponseEntity con datos del pago realizado.
     */
    @PostMapping("/operaciones/paypal/completar/pago")
    public ResponseEntity<?> completarPago(@RequestParam String orderId) {
        Map<String, Object> response = new HashMap<>();
        PaymentPayload paymentPayload;

        try {
            paymentPayload = this.payPalStrategy.capturarOrder(orderId);
        } catch (OperacionException e) {
            response.put("mensaje", "Error al procesar el pago");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("payment", paymentPayload);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

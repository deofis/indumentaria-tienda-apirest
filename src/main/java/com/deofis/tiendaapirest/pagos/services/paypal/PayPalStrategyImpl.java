package com.deofis.tiendaapirest.pagos.services.paypal;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.pagos.PaymentException;
import com.deofis.tiendaapirest.pagos.dto.AmountPayload;
import com.deofis.tiendaapirest.pagos.dto.OrderPayload;
import com.deofis.tiendaapirest.pagos.dto.PayerPayload;
import com.deofis.tiendaapirest.pagos.dto.PaymentPayload;
import com.deofis.tiendaapirest.pagos.factory.OperacionPagoInfo;
import com.deofis.tiendaapirest.pagos.factory.OperacionPagoInfoFactory;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación de {@link com.deofis.tiendaapirest.pagos.services.strategy.PagoStrategy} que realiza el pago
 * conectandose con la API de PayPal, para crear la orden y capturarla al completar el checkout.
 */

@Service
@AllArgsConstructor
@Slf4j
public class PayPalStrategyImpl implements PayPalStrategy {

    private static final String CURRENCY = "USD";
    private static final String INTENT = "CAPTURE";

    private final PayPalHttpClient client;
    private final String clientUrl;

    @Override
    public OrderPayload crearOrder(Operacion operacion) {
        return null;
    }

    @Override
    public PaymentPayload capturarOrder(String orderId) {
        return null;
    }

    @Override
    public OperacionPagoInfo crearPago(Operacion operacion) {
        String CANCEL_REDIRECT_URL = this.clientUrl.concat("/paypal/redirect");
        String APPROVED_REDIRECT_URL = this.clientUrl.concat("/paypal/redirect");
        OrderRequest orderRequest = new OrderRequest();

        ApplicationContext context = new ApplicationContext()
                .landingPage("BILLING")
                .cancelUrl(CANCEL_REDIRECT_URL).userAction("CANCEL")
                .returnUrl(APPROVED_REDIRECT_URL).userAction("CONTINUE");

        orderRequest.checkoutPaymentIntent(INTENT);
        orderRequest.applicationContext(context);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();

        PurchaseUnitRequest unitRequest = new PurchaseUnitRequest();

        AmountWithBreakdown amount = new AmountWithBreakdown();
        String total = String.valueOf(operacion.getTotal());
        amount.currencyCode(CURRENCY);
        amount.value(total);

        unitRequest.amountWithBreakdown(amount);
        purchaseUnitRequests.add(unitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);

        // Atributos para nuestra clase OperacionPagoInfo
        Map<String, Object> atributosPago = new HashMap<>();

        try {
            Order order;
            HttpResponse<Order> response = this.client.execute(request);
            String approveUrl = null;

            order = response.result();

            for (LinkDescription link: order.links()) {
                if (link.rel().equals("approve")) {
                    approveUrl = link.href();
                    break;
                }
            }

            order.links().forEach(link -> log.info(link.rel() + " => " + link.method() + ":" + link.href()));

            // Completamos los datos para nuestra clase de pago.
            atributosPago.put("orderId", order.id());
            atributosPago.put("status", order.status());
            atributosPago.put("approveUrl", approveUrl);
            atributosPago.put("amount", null);
            atributosPago.put("payer", null);
        } catch (IOException e) {
            throw new PaymentException("Error al crear con paypal la orden de pago: " +
                    e.getMessage());
        }

        log.info("Pago creado con éxito");
        return OperacionPagoInfoFactory
                .getOperacionPagoInfo(String.valueOf(operacion.getMedioPago().getNombre()), atributosPago);
    }

    @Override
    public OperacionPagoInfo completarPago(Operacion operacion) {
        String orderId = operacion.getPago().getId();

        OrderRequest orderRequest = new OrderRequest();
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(orderRequest);

        // Creamos mapa de atributos para nuestra clase de pago.
        Map<String, Object> atributosPago = new HashMap<>();
        try {
            HttpResponse<Order> response = this.client.execute(request);
            Order order = response.result();

            PayerPayload payer = PayerPayload.builder()
                    .payerId(order.payer().payerId())
                    .payerEmail(order.payer().email())
                    .payerFullName(order.payer().name().givenName().concat(" ").concat(order.payer().name().surname()))
                    .build();

            AmountPayload amount = AmountPayload.builder()
                    .totalBruto(order.purchaseUnits().get(0).payments().captures().get(0)
                            .sellerReceivableBreakdown().grossAmount().value())
                    .totalNeto(order.purchaseUnits().get(0).payments().captures().get(0)
                            .sellerReceivableBreakdown().netAmount().value())
                    .fee(order.purchaseUnits().get(0).payments().captures().get(0)
                            .sellerReceivableBreakdown().paypalFee().value())
                    .build();

            // Completamos los datos de los atributos para nuestro pago

            atributosPago.put("orderId", order.id());
            atributosPago.put("status", order.status());
            atributosPago.put("amount", amount);
            atributosPago.put("payer", payer);
        } catch (IOException e) {
            throw new PaymentException("Error al completar el pago con paypal: " +
                    e.getMessage());
        }

        log.info("Pago completado con éxito");
        return OperacionPagoInfoFactory
                .getOperacionPagoInfo(String.valueOf(operacion.getMedioPago().getNombre()), atributosPago);
    }
}
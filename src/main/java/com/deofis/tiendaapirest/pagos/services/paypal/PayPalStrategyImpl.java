package com.deofis.tiendaapirest.pagos.services.paypal;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.exceptions.OperacionException;
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
import java.util.*;

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

            return OrderPayload.builder()
                    .id(order.id())
                    .status(order.status())
                    .approveUrl(approveUrl)
                    .build();

        } catch (IOException e) {
            throw new OperacionException("Error al crear la orden de pago: " +
                    e.getMessage());
        }
    }

    @Override
    public PaymentPayload capturarOrder(String orderId) {
        OrderRequest orderRequest = new OrderRequest();
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(orderRequest);

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

            return PaymentPayload.builder()
                    .orderId(order.id())
                    .status(order.status())
                    .payer(payer)
                    .amount(amount)
                    .build();

        } catch (IOException e) {
            throw new OperacionException("Error al completar el pago con paypal: " +
                    e.getMessage());
        }
    }

    @Override
    public OperacionPagoInfo crearPago(Operacion operacion) {
        log.info("Pago creado con éxito");
        Map<String, Object> atributosPago = new HashMap<>();

        atributosPago.put("orderId", UUID.randomUUID().toString());
        atributosPago.put("status", "CREATED");
        atributosPago.put("approveUrl", "https://www.google.com");
        atributosPago.put("amount", AmountPayload.builder()
                .totalBruto(String.valueOf(10.00))
                .totalNeto(String.valueOf(9.75))
                .fee(String.valueOf(0.25)).build());
        atributosPago.put("payer", PayerPayload.builder()
                .payerId(UUID.randomUUID().toString())
                .payerEmail("ezegavilan95@gmail.com")
                .payerFullName("Ezequiel Gavilán").build());

        return OperacionPagoInfoFactory
                .getOperacionPagoInfo(String.valueOf(operacion.getMedioPago().getNombre()), atributosPago);
    }

    @Override
    public OperacionPagoInfo completarPago(String pagoId) {
        log.info("Pago completado con éxito");
        return null;
    }
}

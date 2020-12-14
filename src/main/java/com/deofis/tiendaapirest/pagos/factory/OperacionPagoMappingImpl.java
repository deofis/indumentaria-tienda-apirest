package com.deofis.tiendaapirest.pagos.factory;

import com.deofis.tiendaapirest.pagos.domain.OperacionPago;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OperacionPagoMappingImpl implements OperacionPagoMapping {

    @Override
    public OperacionPago mapToOperacionPago(OperacionPagoInfo pagoInfo) {
        String totalBruto = "";
        String totalNeto = "";
        String fee = "";
        String payerId = "";
        String payerEmail = "";
        String payerFullName = "";

        if (pagoInfo.getAmount() != null) {
            totalBruto = pagoInfo.getAmount().getTotalBruto();
            totalNeto = pagoInfo.getAmount().getTotalNeto();
            fee = pagoInfo.getAmount().getFee();
        }

        if (pagoInfo.getPayer() != null) {
            payerId = pagoInfo.getPayer().getPayerId();
            payerEmail = pagoInfo.getPayer().getPayerEmail();
            payerFullName = pagoInfo.getPayer().getPayerFullName();
        }

        return OperacionPago.builder()
                .id(pagoInfo.getId())
                .nroOperacion(pagoInfo.getNroOperacion())
                .status(pagoInfo.getStatus())
                .approveUrl(pagoInfo.getApproveUrl())
                .totalBruto(totalBruto)
                .totalNeto(totalNeto)
                .fee(fee)
                .payerId(payerId)
                .payerEmail(payerEmail)
                .payerFullName(payerFullName).build();
    }
}

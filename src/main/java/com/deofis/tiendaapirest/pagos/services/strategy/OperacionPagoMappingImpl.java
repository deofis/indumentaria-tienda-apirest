package com.deofis.tiendaapirest.pagos.services.strategy;

import com.deofis.tiendaapirest.pagos.domain.OperacionPago;
import com.deofis.tiendaapirest.pagos.factory.OperacionPagoInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OperacionPagoMappingImpl implements OperacionPagoMapping {

    @Override
    public OperacionPago mapToOperacionPago(OperacionPagoInfo pagoInfo) {
        return OperacionPago.builder()
                .id(pagoInfo.getId())
                .status(pagoInfo.getStatus())
                .approveUrl(pagoInfo.getApproveUrl())
                .totalBruto(pagoInfo.getAmount().getTotalBruto())
                .totalNeto(pagoInfo.getAmount().getTotalNeto())
                .fee(pagoInfo.getAmount().getFee())
                .payerId(pagoInfo.getPayer().getPayerId())
                .payerEmail(pagoInfo.getPayer().getPayerEmail())
                .payerFullName(pagoInfo.getPayer().getPayerFullName()).build();
    }
}

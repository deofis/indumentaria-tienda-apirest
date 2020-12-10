package com.deofis.tiendaapirest.pagos.factory;

import com.deofis.tiendaapirest.pagos.dto.AmountPayload;
import com.deofis.tiendaapirest.pagos.dto.PayerPayload;

import java.util.Map;

public class EfectivoPagoInfo extends OperacionPagoInfo {

    public EfectivoPagoInfo(Map<String, Object> atributos) {
        super(atributos);
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public String getApproveUrl() {
        return null;
    }

    @Override
    public AmountPayload getAmount() {
        return null;
    }

    @Override
    public PayerPayload getPayer() {
        return null;
    }
}

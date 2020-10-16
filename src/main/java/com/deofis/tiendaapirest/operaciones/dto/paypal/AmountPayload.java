package com.deofis.tiendaapirest.operaciones.dto.paypal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmountPayload {

    private String totalBruto;
    private String totalNeto;
    private String paypalFee;
}

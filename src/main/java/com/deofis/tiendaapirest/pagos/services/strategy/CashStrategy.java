package com.deofis.tiendaapirest.pagos.services.strategy;

import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.pagos.factory.OperacionPagoInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CashStrategy implements PagoStrategy {

    @Override
    public OperacionPagoInfo crearPago(Operacion operacion) {
        return null;
    }

    @Override
    public OperacionPagoInfo completarPago(Operacion operacion) {
        return null;
    }
}

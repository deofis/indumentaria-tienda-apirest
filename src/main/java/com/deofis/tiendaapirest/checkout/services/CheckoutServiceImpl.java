package com.deofis.tiendaapirest.checkout.services;

import com.deofis.tiendaapirest.operaciones.domain.EstadoOperacion;
import com.deofis.tiendaapirest.operaciones.domain.EventoOperacion;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.services.OperacionService;
import com.deofis.tiendaapirest.operaciones.services.StateMachineService;
import com.deofis.tiendaapirest.pagos.PaymentException;
import com.deofis.tiendaapirest.pagos.factory.OperacionPagoInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final OperacionService operacionService;
    private final StateMachineService stateMachineService;

    @Transactional
    @Override
    public OperacionPagoInfo ejecutarCheckout(Long nroOperacion) {
        Operacion operacion = this.operacionService.findById(nroOperacion);
        StateMachine<EstadoOperacion, EventoOperacion> sm = this.stateMachineService.build(nroOperacion);

        // Si el pago ya fue efectuado, tiramos excepción
        if (operacion.getPago().getStatus().equalsIgnoreCase("completed"))
            throw new PaymentException("El pago para esta operación ya fue completado");

        // Delegamos el completar pago al State Machine que se encargará de transicionar, al recibir
        // el evento COMPLETE_PAYMENT, y de ejecutar la lógica de negocio correspondiente (completar pago)
        sm.getExtendedState().getVariables().put("operacion", operacion);

        this.stateMachineService.enviarEvento(nroOperacion, sm, EventoOperacion.COMPLETE_PAYMENT);
        this.operacionService.save(operacion);
        return sm.getExtendedState().get("pagoInfo", OperacionPagoInfo.class);
    }
}

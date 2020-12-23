package com.deofis.tiendaapirest.checkout.services;

import com.deofis.tiendaapirest.operaciones.domain.EstadoOperacion;
import com.deofis.tiendaapirest.operaciones.domain.EventoOperacion;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import com.deofis.tiendaapirest.operaciones.services.OperacionService;
import com.deofis.tiendaapirest.operaciones.services.StateMachineService;
import com.deofis.tiendaapirest.pagos.PaymentException;
import com.deofis.tiendaapirest.pagos.domain.MedioPagoEnum;
import com.deofis.tiendaapirest.pagos.factory.OperacionPagoInfo;
import com.deofis.tiendaapirest.pagos.factory.OperacionPagoMapping;
import com.deofis.tiendaapirest.pagos.services.strategy.PagoStrategy;
import com.deofis.tiendaapirest.pagos.services.strategy.PagoStrategyFactory;
import com.deofis.tiendaapirest.pagos.services.strategy.PagoStrategyName;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final OperacionService operacionService;
    private final StateMachineService stateMachineService;

    private final PagoStrategyFactory pagoStrategyFactory;
    private final OperacionPagoMapping operacionPagoMapping;

    @Transactional
    @Override
    public OperacionPagoInfo ejecutarCheckout(Long nroOperacion) {
        Operacion operacion = this.operacionService.findById(nroOperacion);
        StateMachine<EstadoOperacion, EventoOperacion> sm = this.stateMachineService.build(nroOperacion);

        // Si el pago ya fue efectuado, tiramos excepción
        if (operacion.getPago().getStatus().equalsIgnoreCase("completed"))
            throw new PaymentException("El pago para esta operación ya fue completado");

        // Obtenemos esta fecha para asignarsela luego de completar el pago, para
        // que no se pierda al momento de crear el nuevo objeto de pago (son objetos distintos).
        Date fechaCreacionPago = operacion.getPago().getFechaCreacion();

        // Delegamos el completar pago al strategy correspondiente
        PagoStrategy pagoStrategy;
        MedioPagoEnum medioPagoNombre = operacion.getMedioPago().getNombre();

        if (medioPagoNombre.equals(MedioPagoEnum.EFECTIVO))
            pagoStrategy = this.pagoStrategyFactory.get(String.valueOf(PagoStrategyName.cashStrategy));
        else if (medioPagoNombre.equals(MedioPagoEnum.PAYPAL))
            pagoStrategy = this.pagoStrategyFactory.get(String.valueOf(PagoStrategyName.payPalStrategy));
        else pagoStrategy = null;

        OperacionPagoInfo pagoInfo = pagoStrategy != null ? pagoStrategy.completarPago(operacion) : null;
        operacion.setPago(this.operacionPagoMapping.mapToOperacionPago(pagoInfo));

        // Seteamos la fecha de creación guardada para no perder referencia
        operacion.getPago().setFechaCreacion(fechaCreacionPago);
        // Seteamos la fecha de pago al momento actual
        operacion.getPago().setFechaPagado(new Date());

        // Enviamos el EVENTO para transicionar de ESTADO la operación
        this.stateMachineService.enviarEvento(nroOperacion, sm, EventoOperacion.COMPLETE_PAYMENT);

        // Por último, guardamos la operación actualizada y devolvemos el objeto con la info del pago (DTO).
        this.operacionService.save(operacion);
        return pagoInfo;
    }
}

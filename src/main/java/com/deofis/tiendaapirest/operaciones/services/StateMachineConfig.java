package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.operaciones.domain.EstadoOperacion;
import com.deofis.tiendaapirest.operaciones.domain.EventoOperacion;
import com.deofis.tiendaapirest.operaciones.domain.Operacion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.Date;
import java.util.EnumSet;

@Slf4j
@EnableStateMachineFactory
@AllArgsConstructor
@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<EstadoOperacion, EventoOperacion> {

    @Override
    public void configure(StateMachineStateConfigurer<EstadoOperacion, EventoOperacion> states) throws Exception {
        states.withStates()
                .initial(EstadoOperacion.PAYMENT_PENDING)
                .states(EnumSet.allOf(EstadoOperacion.class))
                .end(EstadoOperacion.RECEIVED)
                .end(EstadoOperacion.CANCELLED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EstadoOperacion, EventoOperacion> transitions) throws Exception {
        transitions
                .withExternal().source(EstadoOperacion.PAYMENT_PENDING).target(EstadoOperacion.PAYMENT_DONE).event(EventoOperacion.COMPLETE_PAYMENT)
                .and()
                .withExternal().source(EstadoOperacion.PAYMENT_PENDING).target(EstadoOperacion.CANCELLED).event(EventoOperacion.CANCEL).action(cancelarOperacion())
                .and()
                .withExternal().source(EstadoOperacion.PAYMENT_DONE).target(EstadoOperacion.SENT).event(EventoOperacion.SEND).action(enviar())
                .and()
                .withExternal().source(EstadoOperacion.PAYMENT_DONE).target(EstadoOperacion.CANCELLED).event(EventoOperacion.CANCEL).action(cancelarOperacion())
                .and()
                .withExternal().source(EstadoOperacion.SENT).target(EstadoOperacion.RECEIVED).event(EventoOperacion.RECEIVE).action(recibir())
                .and()
                .withExternal().source(EstadoOperacion.SENT).target(EstadoOperacion.CANCELLED).event(EventoOperacion.CANCEL);
    }

    private Action<EstadoOperacion, EventoOperacion> cancelarOperacion() {
        return stateContext -> {
            // TODO --> Diseñar e implementar lógica de cancelación de operaciones. Tener en cuenta el pago.
        };
    }

    public Action<EstadoOperacion, EventoOperacion> enviar() {
        return stateContext -> {
            StateMachine<EstadoOperacion, EventoOperacion> sm = stateContext.getStateMachine();
            Operacion operacion = sm.getExtendedState().get("operacion", Operacion.class);

            operacion.setFechaEnviada(new Date());
        };
    }

    public Action<EstadoOperacion, EventoOperacion> recibir() {
        return stateContext -> {
            StateMachine<EstadoOperacion, EventoOperacion> sm = stateContext.getStateMachine();
            Operacion operacion = sm.getExtendedState().get("operacion", Operacion.class);

            operacion.setFechaRecibida(new Date());
        };
    }
}

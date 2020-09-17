package com.deofis.tiendaapirest.operacion.services;

import com.deofis.tiendaapirest.operacion.domain.EstadoOperacion;
import com.deofis.tiendaapirest.operacion.domain.EventoOperacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.UUID;

@SpringBootTest
class StateMachineConfigTest {

    @Autowired
    StateMachineFactory<EstadoOperacion, EventoOperacion> factory;

    @Test
    void probarNuevaMaquinaEstados() {
        StateMachine<EstadoOperacion, EventoOperacion> sm = factory.getStateMachine(UUID.randomUUID());

        sm.start();

        System.out.println(sm.getState().toString());

        sm.sendEvent(EventoOperacion.SEND);

        System.out.println(sm.getState().toString());
    }
}
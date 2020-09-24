package com.deofis.tiendaapirest.operaciones.services;

import com.deofis.tiendaapirest.operaciones.domain.EstadoOperacion;
import com.deofis.tiendaapirest.operaciones.domain.EventoOperacion;
import org.springframework.statemachine.StateMachine;

public interface StateMachineService {

    StateMachine<EstadoOperacion, EventoOperacion> build(Long nroOperacion);

    void enviarEvento(Long nroOperacion, StateMachine<EstadoOperacion, EventoOperacion> sm, EventoOperacion evento);
}

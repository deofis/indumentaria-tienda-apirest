package com.deofis.tiendaapirest.autenticacion.service;

import com.deofis.tiendaapirest.autenticacion.dto.AuthResponse;
import com.deofis.tiendaapirest.autenticacion.dto.IniciarSesionRequest;
import com.deofis.tiendaapirest.autenticacion.dto.RegistroUsuarioRequest;

/**
 * Este servicio se encarga de la lógica para: crear un nuevo usuario, iniciar sesión, cerrar sesión.
 */
public interface AutenticacionService {

    void registrarse(RegistroUsuarioRequest registroUsuarioRequest);

    AuthResponse iniciarSesion(IniciarSesionRequest iniciarSesionRequest);


}

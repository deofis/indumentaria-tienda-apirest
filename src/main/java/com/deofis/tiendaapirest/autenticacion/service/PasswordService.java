package com.deofis.tiendaapirest.autenticacion.service;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.dto.CambiarPasswordRequest;

public interface PasswordService {

    /**
     * Cambiar la contraseña del usuario con la sesión actual.
     * @param passwordRequest
     */
    Usuario cambiarPassword(CambiarPasswordRequest passwordRequest);

    void recuperarPassword();
}

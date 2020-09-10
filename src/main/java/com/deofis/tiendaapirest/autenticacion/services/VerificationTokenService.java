package com.deofis.tiendaapirest.autenticacion.services;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.domain.VerificationToken;

/**
 * Servicio para generar tokens de validación.
 */

public interface VerificationTokenService {

    String generarVerificationToken(Usuario usuario);

    VerificationToken getVerificationToken(String token);

    void delete(VerificationToken verificationToken);
}

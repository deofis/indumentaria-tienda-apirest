package com.deofis.tiendaapirest.autenticacion.services;

import com.deofis.tiendaapirest.autenticacion.domain.Usuario;
import com.deofis.tiendaapirest.autenticacion.domain.VerificationToken;
import com.deofis.tiendaapirest.autenticacion.exceptions.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.repositories.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    @Override
    public String generarVerificationToken(Usuario usuario) {
        // Setear expiración.

        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .usuario(usuario)
                .build();

        this.verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Transactional(readOnly = true)
    @Override
    public VerificationToken getVerificationToken(String token) {
        return this.verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new AutenticacionException("Token inválido."));
    }

    @Transactional
    @Override
    public void delete(VerificationToken verificationToken) {
        this.verificationTokenRepository.delete(verificationToken);
    }
}

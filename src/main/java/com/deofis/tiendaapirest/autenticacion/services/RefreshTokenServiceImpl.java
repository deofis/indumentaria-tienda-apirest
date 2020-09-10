package com.deofis.tiendaapirest.autenticacion.services;

import com.deofis.tiendaapirest.autenticacion.domain.RefreshToken;
import com.deofis.tiendaapirest.autenticacion.exceptions.AutenticacionException;
import com.deofis.tiendaapirest.autenticacion.repositories.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;


@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    @Override
    public RefreshToken generarRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setFechaCreacion(new Date());

        return this.refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    @Override
    public void validarRefreshToken(String token) {
        this.refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new AutenticacionException("Refresh Token Inválido"));
    }

    @Transactional
    @Override
    public void eliminarRefreshToken(String token) {
        this.refreshTokenRepository.deleteByToken(token);
    }
}

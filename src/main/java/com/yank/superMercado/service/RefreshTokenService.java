package com.yank.superMercado.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yank.superMercado.model.RefreshToken;
import com.yank.superMercado.model.Usuario;
import com.yank.superMercado.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwt.refresh-token.expiration}")
    private long jwtRefreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(Usuario usuario) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .usuario(usuario)
                .expiresAt(Instant.now().plusMillis(jwtRefreshExpiration))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token no encontrado"));

        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expirado");
        }

        return refreshToken;
    }

    @Transactional
    public void deleteByUsuario(Usuario usuario) {
        refreshTokenRepository.deleteAllByUsuario(usuario);
    }

    // @Transactional
    // @Scheduled(cacheNames = "refreshTokenCleanup", fixedRateString = "${jwt.refresh-expiration-ms}")
    // public void deleteExpiredTokens() {
    //     refreshTokenRepository.deleteAllExpired(Instant.now());
    // }
}

package com.yank.superMercado.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yank.superMercado.dto.request.LoginRequest;
import com.yank.superMercado.dto.request.RefreshTokenRequest;
import com.yank.superMercado.dto.request.RegistroRequest;
import com.yank.superMercado.dto.response.AuthResponse;
import com.yank.superMercado.exception.AuthException;
import com.yank.superMercado.jwt.CustomUserDetails;
import com.yank.superMercado.jwt.JwtService;
import com.yank.superMercado.model.RefreshToken;
import com.yank.superMercado.model.Usuario;
import com.yank.superMercado.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Ya existe un usuario registrado con ese email");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        usuarioRepository.save(usuario);
        return buildAuthResponse(usuario);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("Credenciales inválidas"));

        return buildAuthResponse(usuario);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(request.getRefreshToken());
        Usuario usuario = refreshToken.getUsuario();
        return buildAuthResponse(usuario);
    }

    @Override
    public void logout(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(request.getRefreshToken());
        refreshTokenService.deleteByUsuario(refreshToken.getUsuario());
    }

    private AuthResponse buildAuthResponse(Usuario usuario) {
        CustomUserDetails userDetails = new CustomUserDetails(usuario);
        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(usuario);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(refreshToken.getExpiresAt().toEpochMilli())
                .usuarioId(usuario.getId())
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }
}

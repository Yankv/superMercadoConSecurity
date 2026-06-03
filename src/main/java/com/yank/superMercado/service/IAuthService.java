package com.yank.superMercado.service;

import com.yank.superMercado.dto.request.LoginRequest;
import com.yank.superMercado.dto.request.RefreshTokenRequest;
import com.yank.superMercado.dto.request.RegistroRequest;
import com.yank.superMercado.dto.response.AuthResponse;

public interface IAuthService {
    AuthResponse register(RegistroRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    void logout(RefreshTokenRequest request);
}

package com.triminds.security.auth.application.service;

import org.springframework.stereotype.Service;

import com.triminds.security.auth.AuthResponse;
import com.triminds.security.auth.application.usecase.LoginUseCase;

@Service
public class AuthService implements LoginUseCase {

    private final IdentityAuthClient identityAuthClient;
    private final TokenProvider tokenProvider;

    public AuthService(
        IdentityAuthClient identityAuthClient,
        TokenProvider tokenProvider
    ) {
        this.identityAuthClient = identityAuthClient;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public AuthResponse execute(String email, String password) {

        var identity = identityAuthClient.authenticate(email, password);

        String accessToken = tokenProvider.generateAccessToken(identity);
        String refreshToken = tokenProvider.generateRefreshToken(identity);

        return new AuthResponse(accessToken, refreshToken);
    }
}
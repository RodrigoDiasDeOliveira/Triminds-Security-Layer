package com.triminds.security.auth.application.service;

import org.springframework.stereotype.Service;

import com.triminds.security.auth.application.usecase.LoginUseCase;
import com.triminds.security.auth.domain.model.AuthToken;

@Service
public class LoginService implements LoginUseCase {

    private final IdentityAuthGateway identityGateway;
    private final TokenService tokenService;
    private final SessionService sessionService;

    public LoginService(
            IdentityAuthGateway identityGateway,
            TokenService tokenService,
            SessionService sessionService
    ) {
        this.identityGateway = identityGateway;
        this.tokenService = tokenService;
        this.sessionService = sessionService;
    }

    @Override
    public AuthToken execute(String email, String password) {

        var identity = identityGateway.authenticate(email, password);

        String accessToken = tokenService.generateAccessToken(identity);
        String refreshToken = tokenService.generateRefreshToken(identity);

        sessionService.createSession(identity.getId(), refreshToken);

        return new AuthToken(accessToken, refreshToken);
    }
}
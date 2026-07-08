package com.triminds.security.auth.application.usecase;

import com.triminds.security.auth.application.ports.SessionStorePort;
import com.triminds.security.auth.application.ports.TokenIssuerPort;
import com.triminds.security.auth.domain.AuthSession;
import com.triminds.security.auth.domain.TokenPair;
import com.triminds.security.shared.errors.DomainException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenUseCase {

    private final SessionStorePort sessions;
    private final TokenIssuerPort tokens;

    public RefreshTokenUseCase(SessionStorePort sessions,
                               TokenIssuerPort tokens) {
        this.sessions = sessions;
        this.tokens = tokens;
    }

    public TokenPair execute(String refreshToken) {

        String hash = tokens.hashRefresh(refreshToken);

        AuthSession session = sessions.findByRefreshToken(hash)
                .orElseThrow(() ->
                        new DomainException(
                                "INVALID_REFRESH",
                                "Invalid refresh token"));

        if (session.expiresAt().isBefore(Instant.now())) {
            throw new DomainException(
                    "SESSION_EXPIRED",
                    "Session expired");
        }

        TokenPair pair = tokens.issue(session);

        AuthSession rotated = new AuthSession(
                session.sessionId(),
                session.userId(),
                session.tenantId(),
                session.roles(),
                session.scopes(),
                session.issuedAt(),
                session.expiresAt(),
                tokens.hashRefresh(pair.refreshToken())
        );

        sessions.save(rotated);

        return pair;
    }
}
package com.triminds.auth.application.usecase;

import com.triminds.auth.application.ports.SessionStorePort;
import com.triminds.auth.application.ports.TokenIssuerPort;
import com.triminds.auth.domain.AuthSession;
import com.triminds.auth.domain.TokenPair;
import com.triminds.shared.errors.DomainException;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class RefreshTokenUseCase {
    private final SessionStorePort sessions;
    private final TokenIssuerPort tokens;

    public RefreshTokenUseCase(SessionStorePort sessions, TokenIssuerPort tokens) {
        this.sessions = sessions; this.tokens = tokens;
    }

    public TokenPair execute(String refreshToken) {
        String hash = tokens.hashRefresh(refreshToken);
        AuthSession s = sessions.findByRefreshTokenHash(hash)
                .orElseThrow(() -> new DomainException("INVALID_REFRESH", "Invalid refresh token"));
        if (s.expiresAt().isBefore(Instant.now()))
            throw new DomainException("SESSION_EXPIRED", "Session expired");
        TokenPair pair = tokens.issue(s);
        AuthSession rotated = new AuthSession(s.sessionId(), s.userId(), s.tenantId(),
                s.roles(), s.scopes(), s.issuedAt(), s.expiresAt(), tokens.hashRefresh(pair.refreshToken()));
        sessions.save(rotated);
        return pair;
    }
}

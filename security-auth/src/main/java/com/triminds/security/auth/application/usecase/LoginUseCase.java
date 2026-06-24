package com.triminds.security.auth.application.usecase;

import com.triminds.security.auth.application.ports.*;
import com.triminds.security.auth.domain.AuthSession;
import com.triminds.security.auth.domain.TokenPair;
import com.triminds.security.shared.errors.DomainException;
import com.triminds.security.shared.risk.RiskScore;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginUseCase {
    private final IdentityClientPort identity;
    private final RiskEvaluationPort risk;
    private final TokenIssuerPort tokens;
    private final SessionStorePort sessions;
    private final long sessionTtlSeconds;

    public LoginUseCase(IdentityClientPort identity, RiskEvaluationPort risk,
                        TokenIssuerPort tokens, SessionStorePort sessions) {
        this.identity = identity; this.risk = risk; this.tokens = tokens; this.sessions = sessions;
        this.sessionTtlSeconds = 3600;
    }

    public TokenPair execute(String tenantId, String username, String password, Map<String, Object> ctx) {
        var user = identity.authenticate(tenantId, username, password);
        RiskScore score = risk.evaluate(tenantId, user.userId(), ctx);
        if ("HIGH".equals(score.level()))
            throw new DomainException("RISK_DENIED", "High risk login blocked: " + score.reasons());
        Instant now = Instant.now();
        AuthSession session = new AuthSession(
                UUID.randomUUID().toString(), user.userId(), user.tenantId(),
                user.roles(), List.of("openid", "profile"),
                now, now.plusSeconds(sessionTtlSeconds), null);
        TokenPair pair = tokens.issue(session);
        AuthSession persisted = new AuthSession(session.sessionId(), session.userId(), session.tenantId(),
                session.roles(), session.scopes(), session.issuedAt(), session.expiresAt(),
                tokens.hashRefresh(pair.refreshToken()));
        sessions.save(persisted);
        return pair;
    }
}

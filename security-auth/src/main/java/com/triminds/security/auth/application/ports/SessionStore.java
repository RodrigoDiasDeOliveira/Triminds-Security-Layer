package com.triminds.securityauth.application.ports;

import com.triminds.security.auth.domain.AuthSession;
import java.util.Optional;

public interface SessionStorePort {
    void save(AuthSession session);
    Optional<AuthSession> findById(String sessionId);
    Optional<AuthSession> findByRefreshTokenHash(String hash);
    void revoke(String sessionId);
}

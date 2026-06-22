package com.triminds.auth.application.ports;

import com.triminds.auth.domain.AuthSession;
import java.util.Optional;

public interface SessionStorePort {
    void save(AuthSession session);
    Optional<AuthSession> findById(String sessionId);
    Optional<AuthSession> findByRefreshTokenHash(String hash);
    void revoke(String sessionId);
}

package com.triminds.security.auth.application.ports;

import com.triminds.security.auth.domain.AuthSession;

import java.util.Optional;

public interface SessionStorePort {

    void save(AuthSession session);

    Optional<AuthSession> findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}
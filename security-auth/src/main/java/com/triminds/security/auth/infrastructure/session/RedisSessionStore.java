package com.triminds.security.auth.infrastructure.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triminds.security.auth.application.ports.SessionStorePort;
import com.triminds.security.auth.domain.AuthSession;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Component
public class RedisSessionStore implements SessionStorePort {

    private static final String SESSION_KEY = "auth:session:";
    private static final String REFRESH_KEY = "auth:refresh:";

    private final StringRedisTemplate redis;
    private final ObjectMapper mapper;

    public RedisSessionStore(StringRedisTemplate redis,
                             ObjectMapper mapper) {
        this.redis = redis;
        this.mapper = mapper;
    }

    @Override
    public void save(AuthSession session) {
        try {
            String json = mapper.writeValueAsString(session);

            Duration ttl = Duration.between(Instant.now(), session.expiresAt());
            if (ttl.isNegative() || ttl.isZero()) {
                ttl = Duration.ofSeconds(60);
            }

            redis.opsForValue().set(
                    SESSION_KEY + session.sessionId(),
                    json,
                    ttl
            );

            if (session.refreshToken() != null) {
                redis.opsForValue().set(
                        REFRESH_KEY + session.refreshToken(),
                        session.sessionId(),
                        ttl
                );
            }

        } catch (Exception e) {
            throw new IllegalStateException("Failed to persist session", e);
        }
    }

    private Optional<AuthSession> findById(String sessionId) {

        String json = redis.opsForValue().get(SESSION_KEY + sessionId);

        if (json == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(mapper.readValue(json, AuthSession.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AuthSession> findByRefreshToken(String refreshToken) {

        String sessionId =
                redis.opsForValue().get(REFRESH_KEY + refreshToken);

        if (sessionId == null) {
            return Optional.empty();
        }

        return findById(sessionId);
    }

    @Override
    public void deleteByRefreshToken(String refreshToken) {

        String sessionId =
                redis.opsForValue().get(REFRESH_KEY + refreshToken);

        if (sessionId == null) {
            return;
        }

        findById(sessionId).ifPresent(session -> {
            if (session.refreshToken() != null) {
                redis.delete(REFRESH_KEY + session.refreshToken());
            }
        });

        redis.delete(SESSION_KEY + sessionId);
    }
}
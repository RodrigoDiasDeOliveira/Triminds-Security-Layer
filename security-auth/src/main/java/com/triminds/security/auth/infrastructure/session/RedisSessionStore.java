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

    public RedisSessionStore(StringRedisTemplate redis, ObjectMapper mapper) {
        this.redis = redis; this.mapper = mapper;
    }

    @Override
    public void save(AuthSession s) {
        try {
            String json = mapper.writeValueAsString(s);
            Duration ttl = Duration.between(Instant.now(), s.expiresAt());
            if (ttl.isNegative() || ttl.isZero()) ttl = Duration.ofSeconds(60);
            redis.opsForValue().set(SESSION_KEY + s.sessionId(), json, ttl);
            if (s.refreshTokenHash() != null)
                redis.opsForValue().set(REFRESH_KEY + s.refreshTokenHash(), s.sessionId(), ttl);
        } catch (Exception e) { throw new IllegalStateException("Failed to persist session", e); }
    }

    @Override
    public Optional<AuthSession> findById(String sessionId) {
        String json = redis.opsForValue().get(SESSION_KEY + sessionId);
        if (json == null) return Optional.empty();
        try { return Optional.of(mapper.readValue(json, AuthSession.class)); }
        catch (Exception e) { return Optional.empty(); }
    }

    @Override
    public Optional<AuthSession> findByRefreshTokenHash(String hash) {
        String sid = redis.opsForValue().get(REFRESH_KEY + hash);
        return sid != null ? findById(sid) : Optional.empty();
    }

    @Override
    public void revoke(String sessionId) {
        findById(sessionId).ifPresent(s -> {
            if (s.refreshTokenHash() != null) redis.delete(REFRESH_KEY + s.refreshTokenHash());
        });
        redis.delete(SESSION_KEY + sessionId);
    }
}

package com.triminds.security.accesscontrol.infrastructure.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RedisPermissionCache {

    private final StringRedisTemplate redis;

    private String key(UUID tenantId, UUID identityId, String action, String resource) {
        return tenantId + ":" + identityId + ":" + action + ":" + resource;
    }

    public void put(UUID tenantId, UUID identityId, String action, String resource, boolean allowed) {
        redis.opsForValue().set(
                key(tenantId, identityId, action, resource),
                String.valueOf(allowed),
                Duration.ofMinutes(10)
        );
    }

    public Optional<Boolean> get(UUID tenantId, UUID identityId, String action, String resource) {
        String value = redis.opsForValue().get(
                key(tenantId, identityId, action, resource)
        );

        if (value == null) return Optional.empty();

        return Optional.of(Boolean.parseBoolean(value));
    }

    public void evict(UUID tenantId, UUID identityId) {
        // simplificado: em produção seria scan por prefix
        // aqui mantemos base funcional
    }
}
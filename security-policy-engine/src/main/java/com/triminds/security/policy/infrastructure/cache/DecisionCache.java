package com.triminds.security.policy.infrastructure.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triminds.security.policy.domain.PolicyDecision;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.HexFormat;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DecisionCache {

    private final StringRedisTemplate redis;
    private final ObjectMapper mapper;

    @Value("${policy.cache.ttl-seconds:30}") private long ttl;

    private String hash(Map<String, Object> input) {
        try {
            byte[] bytes = mapper.writeValueAsBytes(input);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(bytes));
        } catch (Exception e) {
            return Integer.toHexString(input.hashCode());
        }
    }

    public Optional<PolicyDecision> get(Map<String, Object> input) {
        try {
            String v = redis.opsForValue().get("policy:" + hash(input));
            if (v == null) return Optional.empty();
            return Optional.of(mapper.readValue(v, PolicyDecision.class));
        } catch (Exception e) { return Optional.empty(); }
    }

    public void put(Map<String, Object> input, PolicyDecision d) {
        try {
            redis.opsForValue().set("policy:" + hash(input),
                    mapper.writeValueAsString(d), Duration.ofSeconds(ttl));
        } catch (JsonProcessingException e) {
            log.debug("cache put failed: {}", e.getMessage());
        }
    }
}

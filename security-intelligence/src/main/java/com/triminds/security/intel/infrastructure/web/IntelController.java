package com.triminds.security.intel.infrastructure.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/intel")
@RequiredArgsConstructor
public class IntelController {
    private final StringRedisTemplate redis;

    @GetMapping("/anomalies/sample-keys")
    public Map<String, Object> sample() {
        Set<String> keys = redis.keys("intel:fail:*");
        return Map.of("activeCounters", keys == null ? 0 : keys.size());
    }
}

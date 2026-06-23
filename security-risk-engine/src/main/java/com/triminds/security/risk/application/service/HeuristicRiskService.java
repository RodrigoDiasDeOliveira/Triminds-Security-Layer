package com.triminds.security.risk.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triminds.security.risk.application.usecase.ScoreRiskUseCase;
import com.triminds.security.risk.domain.RiskScore;
import com.triminds.security.risk.domain.RiskSignal;
import com.triminds.security.shared.events.SecurityEvent;
import com.triminds.security.shared.events.SecurityEventType;
import com.triminds.security.shared.events.SecurityTopics;
import com.triminds.security.shared.events.payload.RiskScorePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

/** Heurísticas v1: novo device, novo país, IP em blacklist local, horário atípico. */
@Slf4j
@Service
@RequiredArgsConstructor
public class HeuristicRiskService implements ScoreRiskUseCase {

    private static final Set<String> BLACKLIST = Set.of("0.0.0.0", "127.0.0.255");

    private final StringRedisTemplate redis;
    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper mapper;

    @Override
    public RiskScore score(RiskSignal s) {
        int total = 0; StringBuilder why = new StringBuilder();

        if (BLACKLIST.contains(s.ip())) { total += 70; why.append("ip-blacklist;"); }

        String knownDevicesKey = "risk:devices:" + s.identityId();
        if (s.deviceFingerprint() != null
                && Boolean.FALSE.equals(redis.opsForSet().isMember(knownDevicesKey, s.deviceFingerprint()))) {
            total += 25; why.append("new-device;");
            redis.opsForSet().add(knownDevicesKey, s.deviceFingerprint());
            redis.expire(knownDevicesKey, Duration.ofDays(180));
        }

        String knownCountriesKey = "risk:geo:" + s.identityId();
        if (s.geoCountry() != null
                && Boolean.FALSE.equals(redis.opsForSet().isMember(knownCountriesKey, s.geoCountry()))) {
            total += 20; why.append("new-country;");
            redis.opsForSet().add(knownCountriesKey, s.geoCountry());
            redis.expire(knownCountriesKey, Duration.ofDays(180));
        }

        int hour = java.time.LocalTime.now().getHour();
        if (hour < 5 || hour > 23) { total += 10; why.append("off-hours;"); }

        var result = RiskScore.of(total, why.toString());
        publish(s, result);
        return result;
    }

    private void publish(RiskSignal s, RiskScore r) {
        try {
            var evt = SecurityEvent.of(SecurityEventType.RISK_SCORE_COMPUTED,
                    s.tenantId(), new RiskScorePayload(s.identityId(), r.score(), r.level(), r.reason()));
            kafka.send(SecurityTopics.EVENTS, s.tenantId().toString(), mapper.writeValueAsString(evt));
        } catch (Exception e) { log.warn("risk publish failed: {}", e.getMessage()); }
    }
}

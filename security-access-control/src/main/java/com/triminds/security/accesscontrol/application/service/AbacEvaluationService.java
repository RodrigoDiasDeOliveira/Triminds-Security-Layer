package com.triminds.security.accesscontrol.application.service;

import com.triminds.security.accesscontrol.application.usecase.EvaluateAbacUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AbacEvaluationService implements EvaluateAbacUseCase {

    @Override
    public boolean execute(UUID tenantId, UUID identityId, Map<String, Object> context) {

        // fase 1: placeholder determinístico

        String risk = (String) context.getOrDefault("risk", "LOW");

        String location = (String) context.getOrDefault("location", "UNKNOWN");

        if ("CRITICAL".equals(risk)) {
            return false;
        }

        if ("UNKNOWN".equals(location)) {
            return false;
        }

        return true;
    }
}
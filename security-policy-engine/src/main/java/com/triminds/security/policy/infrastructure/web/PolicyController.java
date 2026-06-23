package com.triminds.security.policy.infrastructure.web;

import com.triminds.security.policy.application.usecase.EvaluatePolicyUseCase;
import com.triminds.security.policy.application.usecase.ManagePolicyUseCase;
import com.triminds.security.policy.domain.Policy;
import com.triminds.security.policy.domain.PolicyDecision;
import com.triminds.security.shared.web.SecurityHeaders;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
public class PolicyController {

    private final EvaluatePolicyUseCase evaluate;
    private final ManagePolicyUseCase manage;

    @PostMapping("/policy/evaluate")
    public ResponseEntity<PolicyDecision> evaluate(@RequestBody Map<String, Object> input) {
        return ResponseEntity.ok(evaluate.evaluate(input));
    }

    @PostMapping("/policies")
    public ResponseEntity<Policy> create(@RequestHeader(SecurityHeaders.TENANT_ID) UUID tenantId,
                                         @AuthenticationPrincipal Jwt jwt,
                                         @Valid @RequestBody CreatePolicyRequest req) {
        return ResponseEntity.ok(manage.create(tenantId, req.name(), req.description(), req.rego(), jwt.getSubject()));
    }

    @PutMapping("/policies/{id}")
    public ResponseEntity<Policy> update(@RequestHeader(SecurityHeaders.TENANT_ID) UUID tenantId,
                                         @PathVariable UUID id,
                                         @AuthenticationPrincipal Jwt jwt,
                                         @RequestBody UpdatePolicyRequest req) {
        return ResponseEntity.ok(manage.update(tenantId, id, req.rego(), jwt.getSubject()));
    }

    @PostMapping("/policies/{id}/enabled")
    public ResponseEntity<Void> toggle(@RequestHeader(SecurityHeaders.TENANT_ID) UUID tenantId,
                                       @PathVariable UUID id,
                                       @RequestParam boolean enabled) {
        manage.setEnabled(tenantId, id, enabled);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/policies")
    public ResponseEntity<List<Policy>> list(@RequestHeader(SecurityHeaders.TENANT_ID) UUID tenantId) {
        return ResponseEntity.ok(manage.list(tenantId));
    }

    public record CreatePolicyRequest(@NotBlank String name, String description, @NotBlank String rego) {}
    public record UpdatePolicyRequest(@NotBlank String rego) {}
}

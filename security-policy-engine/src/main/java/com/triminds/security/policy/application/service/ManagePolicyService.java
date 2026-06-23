package com.triminds.security.policy.application.service;

import com.triminds.security.policy.application.port.OpaSyncPort;
import com.triminds.security.policy.application.port.PolicyRepositoryPort;
import com.triminds.security.policy.application.usecase.ManagePolicyUseCase;
import com.triminds.security.policy.domain.Policy;
import com.triminds.security.policy.domain.PolicyVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagePolicyService implements ManagePolicyUseCase {

    private final PolicyRepositoryPort repo;
    private final OpaSyncPort opa;

    private String path(UUID tenantId, String name) {
        return "triminds/tenants/" + tenantId + "/" + name;
    }

    @Override @Transactional
    public Policy create(UUID tenantId, String name, String description, String rego, String author) {
        var policy = new Policy(UUID.randomUUID(), tenantId, name, description, 1, true, Instant.now());
        var saved = repo.save(policy);
        repo.saveVersion(new PolicyVersion(UUID.randomUUID(), saved.id(), 1, rego, author, Instant.now()));
        opa.pushPolicy(path(tenantId, name), rego);
        return saved;
    }

    @Override @Transactional
    public Policy update(UUID tenantId, UUID policyId, String rego, String author) {
        var current = repo.findById(tenantId, policyId).orElseThrow();
        int next = current.currentVersion() + 1;
        var updated = new Policy(current.id(), current.tenantId(), current.name(), current.description(),
                                 next, current.enabled(), current.createdAt());
        repo.save(updated);
        repo.saveVersion(new PolicyVersion(UUID.randomUUID(), policyId, next, rego, author, Instant.now()));
        if (updated.enabled()) opa.pushPolicy(path(tenantId, updated.name()), rego);
        return updated;
    }

    @Override @Transactional
    public void setEnabled(UUID tenantId, UUID policyId, boolean enabled) {
        var p = repo.findById(tenantId, policyId).orElseThrow();
        repo.save(new Policy(p.id(), p.tenantId(), p.name(), p.description(),
                             p.currentVersion(), enabled, p.createdAt()));
        if (!enabled) opa.deletePolicy(path(tenantId, p.name()));
        else repo.findVersion(p.id(), p.currentVersion())
                 .ifPresent(v -> opa.pushPolicy(path(tenantId, p.name()), v.rego()));
    }

    @Override
    public List<Policy> list(UUID tenantId) { return repo.findAll(tenantId); }
}

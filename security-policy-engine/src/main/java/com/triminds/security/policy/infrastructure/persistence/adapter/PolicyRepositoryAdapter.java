package com.triminds.security.policy.infrastructure.persistence.adapter;

import com.triminds.security.policy.application.port.PolicyRepositoryPort;
import com.triminds.security.policy.domain.Policy;
import com.triminds.security.policy.domain.PolicyVersion;
import com.triminds.security.policy.infrastructure.persistence.entity.PolicyJpaEntity;
import com.triminds.security.policy.infrastructure.persistence.entity.PolicyVersionJpaEntity;
import com.triminds.security.policy.infrastructure.persistence.repository.PolicyJpaRepository;
import com.triminds.security.policy.infrastructure.persistence.repository.PolicyVersionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PolicyRepositoryAdapter implements PolicyRepositoryPort {

    private final PolicyJpaRepository policies;
    private final PolicyVersionJpaRepository versions;

    private Policy toDomain(PolicyJpaEntity e) {
        return new Policy(e.getId(), e.getTenantId(), e.getName(), e.getDescription(),
                          e.getCurrentVersion(), e.isEnabled(), e.getCreatedAt());
    }
    private PolicyVersion toDomain(PolicyVersionJpaEntity e) {
        return new PolicyVersion(e.getId(), e.getPolicyId(), e.getVersion(), e.getRego(), e.getAuthor(), e.getCreatedAt());
    }

    @Override
    public Policy save(Policy p) {
        var e = policies.save(PolicyJpaEntity.builder()
                .id(p.id()).tenantId(p.tenantId()).name(p.name())
                .description(p.description()).currentVersion(p.currentVersion())
                .enabled(p.enabled()).build());
        return toDomain(e);
    }

    @Override public Optional<Policy> findById(UUID tenantId, UUID id) {
        return policies.findByIdAndTenantId(id, tenantId).map(this::toDomain);
    }

    @Override public List<Policy> findAll(UUID tenantId) {
        return policies.findAllByTenantId(tenantId).stream().map(this::toDomain).toList();
    }

    @Override public PolicyVersion saveVersion(PolicyVersion v) {
        var e = versions.save(PolicyVersionJpaEntity.builder()
                .id(v.id()).policyId(v.policyId()).version(v.version()).rego(v.rego()).author(v.author()).build());
        return toDomain(e);
    }

    @Override public Optional<PolicyVersion> findVersion(UUID policyId, int version) {
        return versions.findByPolicyIdAndVersion(policyId, version).map(this::toDomain);
    }

    @Override public List<Policy> findAllEnabled() {
        return policies.findAllByEnabledTrue().stream().map(this::toDomain).toList();
    }
}

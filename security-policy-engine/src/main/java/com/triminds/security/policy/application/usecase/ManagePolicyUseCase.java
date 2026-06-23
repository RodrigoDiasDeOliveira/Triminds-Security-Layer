package com.triminds.security.policy.application.usecase;

import com.triminds.security.policy.domain.Policy;
import java.util.List;
import java.util.UUID;

public interface ManagePolicyUseCase {
    Policy create(UUID tenantId, String name, String description, String rego, String author);
    Policy update(UUID tenantId, UUID policyId, String rego, String author);
    void   setEnabled(UUID tenantId, UUID policyId, boolean enabled);
    List<Policy> list(UUID tenantId);
}

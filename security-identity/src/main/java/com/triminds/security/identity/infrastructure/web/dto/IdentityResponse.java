package com.triminds.security.identity.infrastructure.web.dto;
import com.triminds.security.identity.domain.model.Identity;
import java.time.Instant; import java.util.*; 
public record IdentityResponse(String id, String organizationId, String username, String email, String displayName,
        String type, String status, Set<String> groups, Map<String,String> attributes,
        FederationSummary federation, Instant createdAt, Instant updatedAt, Instant lastLoginAt) {
    public record FederationSummary(String provider, String issuer) {}
    public static IdentityResponse from(Identity i) {
        FederationSummary fed = i.isFederated() ? new FederationSummary(i.getFederationInfo().provider().name(), i.getFederationInfo().issuer()) : null;
        return new IdentityResponse(i.getId().toString(), i.getOrganizationId().toString(), i.getUsername(), i.getEmail(),
            i.getDisplayName(), i.getType().name(), i.getStatus().name(), i.getGroups(), i.getAttributes(), fed,
            i.getCreatedAt(), i.getUpdatedAt(), i.getLastLoginAt());
    }
}

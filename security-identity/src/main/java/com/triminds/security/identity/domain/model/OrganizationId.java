package com.triminds.security.identity.domain.model;
import java.util.Objects;
import java.util.UUID;
record OrganizationId(UUID value) {
    OrganizationId { Objects.requireNonNull(value); }
    static OrganizationId of(String uuid) { return new OrganizationId(UUID.fromString(uuid)); }
    static OrganizationId of(UUID uuid) { return new OrganizationId(uuid); }
    @Override public String toString() { return value.toString(); }
}

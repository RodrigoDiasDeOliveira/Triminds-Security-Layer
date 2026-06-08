package com.triminds.security.identity.domain.model;
import java.util.Objects;
import java.util.UUID;
record IdentityId(UUID value) {
    IdentityId { Objects.requireNonNull(value); }
    static IdentityId generate() { return new IdentityId(UUID.randomUUID()); }
    static IdentityId of(String uuid) { return new IdentityId(UUID.fromString(uuid)); }
    static IdentityId of(UUID uuid) { return new IdentityId(uuid); }
    @Override public String toString() { return value.toString(); }
}

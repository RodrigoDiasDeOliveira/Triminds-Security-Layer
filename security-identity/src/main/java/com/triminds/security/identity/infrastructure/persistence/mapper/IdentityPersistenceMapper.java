package com.triminds.security.identity.infrastructure.persistence.mapper;

import com.triminds.security.identity.domain.model.*;
import com.triminds.security.identity.infrastructure.persistence.entity.IdentityJpaEntity;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.*;

@Component
public class IdentityPersistenceMapper {

    public IdentityJpaEntity toJpa(Identity identity) {
        IdentityJpaEntity e = new IdentityJpaEntity();
        e.setId(UUID.fromString(identity.getId().toString()));
        e.setOrganizationId(UUID.fromString(identity.getOrganizationId().toString()));
        e.setUsername(identity.getUsername()); e.setEmail(identity.getEmail());
        e.setDisplayName(identity.getDisplayName()); e.setType(identity.getType()); e.setStatus(identity.getStatus());
        e.setGroups(new HashSet<>(identity.getGroups())); e.setAttributes(new HashMap<>(identity.getAttributes()));
        e.setCreatedAt(identity.getCreatedAt()); e.setUpdatedAt(identity.getUpdatedAt());
        e.setLastLoginAt(identity.getLastLoginAt()); e.setFailedLoginAttempts(identity.getFailedLoginAttempts());
        e.setLockedUntil(identity.getLockedUntil());
        if (identity.isFederated()) {
            FederationInfo fi = identity.getFederationInfo();
            e.setFederationProvider(fi.provider().name()); e.setFederationExternalId(fi.externalId());
            e.setFederationIssuer(fi.issuer()); e.setFederationSubject(fi.subject());
        }
        return e;
    }

    public Identity toDomain(IdentityJpaEntity e) {
        try {
            var ctor = Identity.class.getDeclaredConstructor(); ctor.setAccessible(true);
            Identity i = ctor.newInstance();
            set(i, "id", IdentityId.of(e.getId())); set(i, "organizationId", OrganizationId.of(e.getOrganizationId()));
            set(i, "username", e.getUsername()); set(i, "email", e.getEmail()); set(i, "displayName", e.getDisplayName());
            set(i, "type", e.getType()); set(i, "status", e.getStatus());
            set(i, "createdAt", e.getCreatedAt()); set(i, "updatedAt", e.getUpdatedAt());
            set(i, "lastLoginAt", e.getLastLoginAt()); set(i, "failedLoginAttempts", e.getFailedLoginAttempts());
            set(i, "lockedUntil", e.getLockedUntil());
            getSet(i, "groups").addAll(e.getGroups());
            getMap(i, "attributes").putAll(e.getAttributes());
            if (e.getFederationProvider() != null) {
                set(i, "federationInfo", new FederationInfo(FederationInfo.FederationProvider.valueOf(e.getFederationProvider()),
                        e.getFederationExternalId(), e.getFederationIssuer(), e.getFederationSubject()));
            }
            return i;
        } catch (Exception ex) { throw new IllegalStateException("Falha ao reconstituir Identity: " + e.getId(), ex); }
    }

    private void set(Object t, String name, Object val) throws Exception {
        Field f = findField(t.getClass(), name); f.setAccessible(true); f.set(t, val);
    }
    @SuppressWarnings("unchecked")
    private Set<String> getSet(Object t, String name) throws Exception {
        Field f = findField(t.getClass(), name); f.setAccessible(true); return (Set<String>) f.get(t);
    }
    @SuppressWarnings("unchecked")
    private Map<String, String> getMap(Object t, String name) throws Exception {
        Field f = findField(t.getClass(), name); f.setAccessible(true); return (Map<String, String>) f.get(t);
    }
    private Field findField(Class<?> c, String n) throws NoSuchFieldException {
        try { return c.getDeclaredField(n); } catch (NoSuchFieldException e) {
            if (c.getSuperclass() != null) return findField(c.getSuperclass(), n); throw e;
        }
    }
}

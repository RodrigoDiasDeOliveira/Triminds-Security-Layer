package com.triminds.security.identity.application.usecase;

import com.triminds.security.identity.application.ports.*;
import com.triminds.security.identity.domain.Credential;
import com.triminds.security.identity.domain.Identity;
import com.triminds.shared.errors.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateIdentityUseCase {
    private final IdentityRepositoryPort identities;
    private final CredentialRepositoryPort credentials;
    private final PasswordHasherPort hasher;
    private final IdentityEventPublisherPort events;

    public CreateIdentityUseCase(IdentityRepositoryPort identities, CredentialRepositoryPort credentials,
                                 PasswordHasherPort hasher, IdentityEventPublisherPort events) {
        this.identities = identities; this.credentials = credentials; this.hasher = hasher; this.events = events;
    }

    @Transactional
    public Identity execute(String tenantId, String username, String email, String rawPassword) {
        if (identities.existsByTenantAndUsername(tenantId, username))
            throw new DomainException("IDENTITY_EXISTS", "Username already exists for tenant");
        Identity id = identities.save(Identity.create(tenantId, username, email));
        credentials.save(Credential.of(id.getId(), hasher.hash(rawPassword)));
        events.identityCreated(id);
        return id;
    }
}

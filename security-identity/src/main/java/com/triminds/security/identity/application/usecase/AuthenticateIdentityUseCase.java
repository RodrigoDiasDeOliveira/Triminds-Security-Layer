package com.triminds.security.identity.application.usecase.triminds.identity.application.usecase;

import com.triminds.security.identity.application.ports.*;
import com.triminds.security.identity.domain.Identity;
import com.triminds.security.identity.domain.IdentityStatus;
import com.triminds.security.shared.errors.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticateIdentityUseCase {
    private final IdentityRepositoryPort identities;
    private final CredentialRepositoryPort credentials;
    private final PasswordHasherPort hasher;
    private final IdentityEventPublisherPort events;

    public AuthenticateIdentityUseCase(IdentityRepositoryPort identities, CredentialRepositoryPort credentials,
                                       PasswordHasherPort hasher, IdentityEventPublisherPort events) {
        this.identities = identities; this.credentials = credentials; this.hasher = hasher; this.events = events;
    }

    @Transactional
    public Identity execute(String tenantId, String username, String rawPassword) {
        Identity i = identities.findByTenantAndUsername(tenantId, username)
                .orElseThrow(() -> new DomainException("INVALID_CREDENTIALS", "Invalid credentials"));
        if (i.getStatus() == IdentityStatus.LOCKED) throw new DomainException("LOCKED", "Account locked");
        if (i.getStatus() == IdentityStatus.DISABLED) throw new DomainException("DISABLED", "Account disabled");
        var cred = credentials.findByIdentityId(i.getId())
                .orElseThrow(() -> new DomainException("INVALID_CREDENTIALS", "Invalid credentials"));
        if (!hasher.matches(rawPassword, cred.passwordHash())) {
            i.registerFailedAttempt();
            identities.save(i);
            if (i.getStatus() == IdentityStatus.LOCKED) events.identityLocked(i);
            throw new DomainException("INVALID_CREDENTIALS", "Invalid credentials");
        }
        i.resetFailedAttempts();
        return identities.save(i);
    }
}

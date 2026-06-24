package com.triminds.security.identity.application.usecase;

import com.triminds.security.identity.application.ports.IdentityEventPublisherPort;
import com.triminds.security.identity.application.ports.IdentityRepositoryPort;
import com.triminds.security.identity.domain.Identity;
import com.triminds.shared.errors.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class ActivateIdentityUseCase {
    private final IdentityRepositoryPort identities;
    private final IdentityEventPublisherPort events;

    public ActivateIdentityUseCase(IdentityRepositoryPort identities, IdentityEventPublisherPort events) {
        this.identities = identities; this.events = events;
    }

    @Transactional
    public Identity execute(UUID id) {
        Identity i = identities.findById(id).orElseThrow(() -> new DomainException("NOT_FOUND", "Identity not found"));
        i.activate();
        Identity saved = identities.save(i);
        events.identityActivated(saved);
        return saved;
    }
}

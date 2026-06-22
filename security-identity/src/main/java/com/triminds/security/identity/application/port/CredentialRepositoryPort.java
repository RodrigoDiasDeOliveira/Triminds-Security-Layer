package com.triminds.security.identity.application.ports;

import com.triminds.identity.domain.Credential;
import java.util.Optional;
import java.util.UUID;

public interface CredentialRepositoryPort {
    Credential save(Credential credential);
    Optional<Credential> findByIdentityId(UUID identityId);
}

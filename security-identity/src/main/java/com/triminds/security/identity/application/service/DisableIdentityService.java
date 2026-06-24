package com.triminds.security.identity.application.service;

import org.springframework.stereotype.Service;


import com.triminds.security.identity.domain.model.Identity;

@Service
public class DisableIdentityService implements DisableIdentityUseCase {

    private final IdentityRepository repository;

    public DisableIdentityService(IdentityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Identity execute(IdentityId identityId, String reason) {

        Identity identity = repository.findById(identityId)
                .orElseThrow(() -> new RuntimeException("Identity not found"));

        identity.disable(reason); // domain method

        repository.save(identity);

        identity.pullEvents();

        return identity;
    }
}
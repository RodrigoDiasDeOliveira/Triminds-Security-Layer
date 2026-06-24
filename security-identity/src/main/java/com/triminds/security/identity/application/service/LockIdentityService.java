package com.triminds.security.identity.application.service;

import org.springframework.stereotype.Service;

import com.triminds.security.identity.domain.Identity;


@Service
public class LockIdentityService implements LockIdentityUseCase {

    private final IdentityRepository repository;

    public LockIdentityService(IdentityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Identity execute(IdentityId identityId, String reason) {

        Identity identity = repository.findById(identityId)
                .orElseThrow(() -> new RuntimeException("Identity not found"));

        identity.lock(reason); // domain method

        repository.save(identity);

        identity.pullEvents();

        return identity;
    }
}
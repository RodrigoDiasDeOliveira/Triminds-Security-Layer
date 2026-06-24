package com.triminds.security.identity.application.service;

import org.springframework.stereotype.Service;

import com.triminds.security.identity.application.usecase.ActivateIdentityUseCase;
import com.triminds.security.identity.domain.Identity;
import com.triminds.security.identity.domain.IdentityId;


@Service
public class ActivateIdentityService implements ActivateIdentityUseCase {

    private final IdentityRepository repository;

    public ActivateIdentityService(IdentityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Identity execute(IdentityId identityId) {

        Identity identity = repository.findById(identityId)
                .orElseThrow(() -> new RuntimeException("Identity not found"));

        identity.activate(); // domain method

        repository.save(identity);

        identity.pullEvents();

        return identity;
    }
}
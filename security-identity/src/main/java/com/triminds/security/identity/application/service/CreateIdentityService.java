package com.triminds.security.identity.application.service;

import org.springframework.stereotype.Service;

import com.triminds.security.identity.application.usecase.CreateIdentityUseCase;
import com.triminds.security.identity.domain.Identity;


@Service
public class CreateIdentityService implements CreateIdentityUseCase {

    private final IdentityRepository repository;

    public CreateIdentityService(IdentityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Identity execute(String email, String password) {

        if (repository.existsByEmail(email)) {
            throw new RuntimeException("Identity already exists: " + email);
        }

        Identity identity = Identity.create(email, password);

        repository.save(identity);

        identity.pullEvents();

        return identity;
    }
}
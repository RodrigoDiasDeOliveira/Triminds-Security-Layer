package com.triminds.security.identity.application.usecase;

import com.triminds.security.identity.domain.model.Identity;

public interface CreateIdentityUseCase {
    Identity execute(String email, String password);
}
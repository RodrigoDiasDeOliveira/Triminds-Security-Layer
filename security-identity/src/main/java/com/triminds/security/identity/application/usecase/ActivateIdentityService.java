package com.triminds.security.identity.application.usecase;

import com.triminds.security.identity.domain.model.Identity;
import com.triminds.security.identity.domain.model.IdentityId;

public interface ActivateIdentityUseCase {
    Identity execute(IdentityId identityId);
}
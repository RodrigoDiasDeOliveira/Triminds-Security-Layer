package com.triminds.security.identity.application.usecase;

import com.triminds.security.identity.domain.model.Identity;
import com.triminds.security.identity.domain.model.IdentityId;

public interface LockIdentityUseCase {
    Identity execute(IdentityId identityId, String reason);
}
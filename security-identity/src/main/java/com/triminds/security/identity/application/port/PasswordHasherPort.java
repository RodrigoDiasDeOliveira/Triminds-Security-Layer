package com.triminds.identity.application.ports;

import com.triminds.identity.domain.Identity;

public interface IdentityEventPublisherPort {
    void identityCreated(Identity i);
    void identityActivated(Identity i);
    void identityDisabled(Identity i);
    void identityLocked(Identity i);
}

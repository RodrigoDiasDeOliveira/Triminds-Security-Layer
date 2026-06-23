package com.triminds.security.sdk;

import java.util.UUID;

/** Implementado pelo app (ex.: leitura do JWT atual). */
public interface SecurityCallContext {
    UUID tenantId();
    UUID identityId();
}

package com.triminds.security.shared.tenant;

import java.util.UUID;

/** Acesso ao tenant corrente via ThreadLocal (preenchido por filter no gateway / em cada serviço). */
public final class TenantContext {
    private static final ThreadLocal<UUID> CURRENT = new ThreadLocal<>();
    public static void set(UUID tenantId) { CURRENT.set(tenantId); }
    public static UUID get() { return CURRENT.get(); }
    public static void clear() { CURRENT.remove(); }
    private TenantContext() {}
}

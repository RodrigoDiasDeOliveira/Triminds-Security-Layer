package com.triminds.shared.tenant;

public final class TenantHolder {
    private static final ThreadLocal<TenantContext> CTX = new ThreadLocal<>();
    private TenantHolder() {}
    public static void set(TenantContext ctx) { CTX.set(ctx); }
    public static TenantContext get() { return CTX.get(); }
    public static String tenantId() { return CTX.get() != null ? CTX.get().tenantId() : null; }
    public static void clear() { CTX.remove(); }
}

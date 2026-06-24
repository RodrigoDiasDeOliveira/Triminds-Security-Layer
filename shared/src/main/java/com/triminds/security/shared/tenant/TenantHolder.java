package com.triminds.security.shared.tenant;


public final class TenantHolder {


    private static final ThreadLocal<TenantContext> CTX =
            new ThreadLocal<>();


    private TenantHolder() {
    }


    public static void set(
            TenantContext context
    ) {

        CTX.set(context);
    }


    public static TenantContext get() {

        return CTX.get();
    }


    public static String tenantId() {

        TenantContext context = CTX.get();

        return context != null
                ? context.tenantId()
                : null;
    }


    public static void clear() {

        CTX.remove();
    }
}
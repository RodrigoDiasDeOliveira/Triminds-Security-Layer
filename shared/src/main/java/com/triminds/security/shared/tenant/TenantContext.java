package com.triminds.security.shared.tenant;


public record TenantContext(

        String tenantId,

        String correlationId

) {


    public static TenantContext of(
            String tenantId,
            String correlationId
    ) {

        return new TenantContext(
                tenantId,
                correlationId
        );
    }
}
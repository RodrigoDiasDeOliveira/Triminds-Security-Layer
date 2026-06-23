package com.triminds.security.sdk;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.UUID;

/** Intercepta métodos anotados com @RequirePermission e chama o access-control. */
@Aspect
@Component
public class RequirePermissionAspect {

    private final AccessControlSdkClient client;
    private final SecurityCallContext context;

    public RequirePermissionAspect(AccessControlSdkClient client, SecurityCallContext context) {
        this.client = client; this.context = context;
    }

    @Around("@annotation(com.triminds.security.sdk.RequirePermission)")
    public Object enforce(ProceedingJoinPoint pjp) throws Throwable {
        var sig = (MethodSignature) pjp.getSignature();
        var ann = sig.getMethod().getAnnotation(RequirePermission.class);
        UUID tenantId   = context.tenantId();
        UUID identityId = context.identityId();
        var resp = client.check(tenantId, identityId, ann.action(), ann.resource());
        if (!Boolean.TRUE.equals(resp.get("allowed"))) {
            throw new SecurityException("forbidden: " + ann.action() + " " + ann.resource());
        }
        return pjp.proceed();
    }
}

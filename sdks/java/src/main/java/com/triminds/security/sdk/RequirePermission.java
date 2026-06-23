package com.triminds.security.sdk;

import java.lang.annotation.*;

/** Marca um método cuja execução exige permissão (action,resource) no security-access-control. */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {
    String action();
    String resource();
}

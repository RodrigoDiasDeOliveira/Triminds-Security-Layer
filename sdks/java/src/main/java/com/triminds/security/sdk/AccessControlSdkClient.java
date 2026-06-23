package com.triminds.security.sdk;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "triminds-access-control", url = "${triminds.access-control.url}")
public interface AccessControlSdkClient {

    @GetMapping("/access/check")
    Map<String, Object> check(@RequestHeader("X-Tenant-Id") UUID tenantId,
                              @RequestParam UUID identityId,
                              @RequestParam String action,
                              @RequestParam String resource);
}

package com.triminds.security.auth.infrastructure.client;

import com.triminds.security.auth.application.ports.IdentityClientPort;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@Component
public class IdentityFeignClient implements IdentityClientPort {

    @FeignClient(name = "security-identity", url = "${triminds.clients.identity.url:http://localhost:8081}")
    interface Api {
        @PostMapping("/api/v1/identities/authenticate")
        Map<String, Object> authenticate(@RequestHeader("X-Tenant-Id") String tenant,
                                         @RequestBody Map<String, String> body);
    }

    private final Api api;
    public IdentityFeignClient(Api api) { this.api = api; }

    @Override
    public AuthenticatedUser authenticate(String tenantId, String username, String password) {
        Map<String, Object> resp = api.authenticate(tenantId, Map.of("username", username, "password", password));
        return new AuthenticatedUser(
                (String) resp.get("id"), (String) resp.get("tenantId"),
                (String) resp.get("username"), List.of("ROLE_USER"));
    }
}

package com.triminds.security.gateway.client;

import com.triminds.security.shared.web.SecurityHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Component
public class AccessControlClient {

    private final WebClient client;

    public AccessControlClient(WebClient.Builder builder,
                               @Value("${access-control.url}") String baseUrl) {
        this.client = builder.baseUrl(baseUrl).build();
    }

    public Mono<Boolean> check(UUID tenantId, UUID identityId, String action, String resource) {
        return client.get()
                .uri(uri -> uri.path("/access/check")
                        .queryParam("identityId", identityId)
                        .queryParam("action", action)
                        .queryParam("resource", resource).build())
                .header(SecurityHeaders.TENANT_ID, tenantId.toString())
                .retrieve()
                .bodyToMono(Map.class)
                .map(m -> Boolean.TRUE.equals(m.get("allowed")))
                .onErrorReturn(false);
    }
}

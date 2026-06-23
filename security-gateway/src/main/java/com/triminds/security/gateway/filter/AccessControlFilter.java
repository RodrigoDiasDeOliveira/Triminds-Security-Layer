package com.triminds.security.gateway.filter;

import com.triminds.security.gateway.client.AccessControlClient;
import com.triminds.security.shared.web.SecurityHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/** Para cada rota protegida, consulta /access/check no security-access-control. */
@Component
@RequiredArgsConstructor
public class AccessControlFilter implements GlobalFilter, Ordered {

    private final AccessControlClient access;

    @Value("${access-control.enforce-prefixes:/api/}") private String enforcePrefix;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        if (!path.startsWith(enforcePrefix)) return chain.filter(exchange);

        String tenant   = exchange.getRequest().getHeaders().getFirst(SecurityHeaders.TENANT_ID);
        String identity = exchange.getRequest().getHeaders().getFirst(SecurityHeaders.IDENTITY_ID);
        if (tenant == null || identity == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String action   = exchange.getRequest().getMethod().name();
        String resource = path;

        return access.check(UUID.fromString(tenant), UUID.fromString(identity), action, resource)
                .flatMap(allowed -> {
                    if (Boolean.TRUE.equals(allowed)) return chain.filter(exchange);
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                });
    }

    @Override public int getOrder() { return 0; }
}

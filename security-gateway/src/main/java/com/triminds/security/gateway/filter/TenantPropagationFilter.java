package com.triminds.security.gateway.filter;

import com.triminds.security.shared.web.SecurityHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/** Propaga tenant + identity vindos do JWT como headers para downstream services. */
@Component
public class TenantPropagationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
            .map(ctx -> ctx.getAuthentication())
            .cast(JwtAuthenticationToken.class)
            .map(t -> (Jwt) t.getPrincipal())
            .map(jwt -> exchange.mutate().request(r -> r
                .header(SecurityHeaders.TENANT_ID,  String.valueOf(jwt.getClaimAsString("tid")))
                .header(SecurityHeaders.IDENTITY_ID, jwt.getSubject())
                .header(SecurityHeaders.REQUEST_ID, UUID.randomUUID().toString())
            ).build())
            .defaultIfEmpty(exchange)
            .flatMap(chain::filter);
    }

    @Override public int getOrder() { return -10; }
}

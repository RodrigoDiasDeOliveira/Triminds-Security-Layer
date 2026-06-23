package com.triminds.security.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Configuration
@EnableWebFluxSecurity
public class GatewayConfig {

    @Bean
    public SecurityWebFilterChain chain(ServerHttpSecurity http) {
        http.csrf(c -> c.disable())
            .authorizeExchange(a -> a
                .pathMatchers("/actuator/health/**","/actuator/prometheus","/auth/**","/oauth2/**","/.well-known/**").permitAll()
                .anyExchange().authenticated())
            .oauth2ResourceServer(o -> o.jwt(j -> {}));
        return http.build();
    }

    @Bean
    public RedisRateLimiter rateLimiter() {
        return new RedisRateLimiter(50, 100); // 50 rps, burst 100
    }

    /** Chave do rate-limit: identityId do JWT, ou IP. */
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> exchange.getPrincipal()
                .map(p -> p.getName())
                .switchIfEmpty(Mono.justOrEmpty(Optional.ofNullable(exchange.getRequest().getRemoteAddress())
                        .map(a -> a.getAddress().getHostAddress())))
                .defaultIfEmpty("anonymous");
    }
}

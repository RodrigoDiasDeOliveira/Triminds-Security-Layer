package com.triminds.security.gateway.admin.config;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * GlobalFilter reativo para multi-tenancy + request ID no Spring Cloud Gateway
 */
@Component
public class TenantGlobalFilter implements GlobalFilter, Ordered {

    public static final String TENANT_HEADER = "X-Tenant-Id";
    public static final String REQUEST_ID_HEADER = "X-Request-Id";
    public static final String TENANT_ATTR = "triminds.tenantId";
    public static final String REQUEST_ID_ATTR = "triminds.requestId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String tenant = request.getHeaders().getFirst(TENANT_HEADER);
        String requestId = request.getHeaders().getFirst(REQUEST_ID_HEADER);

        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }

        // Adiciona no MDC (para logs)
        if (tenant != null && !tenant.isBlank()) {
            MDC.put("tenant", tenant);
        }
        MDC.put("requestId", requestId);

        // Cria uma nova request com headers e atributos
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header(REQUEST_ID_HEADER, requestId)
                .build();

        // Adiciona atributos no exchange (equivalente ao setAttribute no servlet)
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();

        if (tenant != null && !tenant.isBlank()) {
            mutatedExchange.getAttributes().put(TENANT_ATTR, tenant);
        }
        mutatedExchange.getAttributes().put(REQUEST_ID_ATTR, requestId);

        return chain.filter(mutatedExchange)
                .doFinally(signalType -> {
                    // Limpeza do MDC
                    MDC.remove("tenant");
                    MDC.remove("requestId");
                });
    }

    @Override
    public int getOrder() {
        return 1; // Mesma ordem do filtro anterior
    }
}
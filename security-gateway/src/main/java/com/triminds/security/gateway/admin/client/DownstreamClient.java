package com.triminds.security.gateway.admin.client;

import com.triminds.security.gateway.admin.config.DownstreamProperties;
import com.triminds.security.gateway.admin.config.TenantGlobalFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Cliente HTTP reativo que propaga Authorization, X-Tenant-Id e X-Request-Id
 * para os módulos downstream.
 */
@Configuration
@EnableConfigurationProperties(DownstreamProperties.class)
public class DownstreamClient {

    @Bean
    public WebClient downstreamWebClient(DownstreamProperties props) {
        return WebClient.builder()
                .filter((request, next) -> {
                    ClientRequest filteredRequest = propagateHeaders(request);
                    return next.exchange(filteredRequest);
                })
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Propaga headers de segurança e tenant do contexto reativo.
     */
    private ClientRequest propagateHeaders(ClientRequest original) {
        ClientRequest.Builder builder = ClientRequest.from(original);

        // Propagação via Reactor Context (melhor prática no WebFlux)
        return Mono.deferContextual(ctx -> {
            // Propaga Authorization
            ctx.getOrEmpty("Authorization").ifPresent(auth ->
                builder.header(HttpHeaders.AUTHORIZATION, auth.toString())
            );

            // Propaga Tenant
            ctx.getOrEmpty(TenantGlobalFilter.TENANT_HEADER).ifPresent(tenant ->
                builder.header(TenantGlobalFilter.TENANT_HEADER, tenant.toString())
            );

            // Propaga Request ID
            ctx.getOrEmpty(TenantGlobalFilter.REQUEST_ID_HEADER).ifPresent(rid ->
                builder.header(TenantGlobalFilter.REQUEST_ID_HEADER, rid.toString())
            );

            return Mono.just(builder.build());
        }).contextWrite(ctx -> ctx).block(); // Executa no contexto atual
    }

    /**
     * Helper para chamadas simples de proxy.
     */
    public static Mono<String> proxy(WebClient client, HttpMethod method, String baseUrl, String path,
                                     Map<String, String> queryParams, String body) {

        return client.method(method)
                .uri(uriBuilder -> {
                    var uri = uriBuilder
                            .scheme(null)
                            .host(null)
                            .path(baseUrl.replaceAll("/$", "") + path);

                    if (queryParams != null) {
                        queryParams.forEach((k, v) -> {
                            if (v != null) uri.queryParam(k, v);
                        });
                    }
                    return uri.build();
                })
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body != null ? body : "")
                .retrieve()
                .bodyToMono(String.class);
    }
}
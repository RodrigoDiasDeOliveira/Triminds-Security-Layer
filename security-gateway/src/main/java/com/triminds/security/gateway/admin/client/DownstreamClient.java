package com.triminds.gateway.admin.client;
import com.triminds.gateway.admin.config.DownstreamProperties;
import com.triminds.gateway.admin.config.TenantFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;
import java.util.Optional;
/**
 * Cliente HTTP que propaga Authorization, X-Tenant-Id e X-Request-Id
 * para os módulos downstream.
 */
@Configuration
@EnableConfigurationProperties(DownstreamProperties.class)
public class DownstreamClient {
    @Bean
    public WebClient downstreamWebClient(DownstreamProperties props) {
        return WebClient.builder()
            .filter((req, next) -> {
                Optional<HttpServletRequest> current = currentRequest();
                var b = org.springframework.web.reactive.function.client.ClientRequest.from(req);
                current.ifPresent(http -> {
                    String auth = http.getHeader(HttpHeaders.AUTHORIZATION);
                    if (auth != null) b.header(HttpHeaders.AUTHORIZATION, auth);
                    Object tenant = http.getAttribute(TenantFilter.TENANT_ATTR);
                    if (tenant != null) b.header(TenantFilter.TENANT_HEADER, tenant.toString());
                    Object rid = http.getAttribute(TenantFilter.REQUEST_ID_ATTR);
                    if (rid != null) b.header(TenantFilter.REQUEST_ID_HEADER, rid.toString());
                });
                return next.exchange(b.build());
            })
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
    private static Optional<HttpServletRequest> currentRequest() {
        var attrs = RequestContextHolder.getRequestAttributes();
        if (attrs instanceof ServletRequestAttributes sra) return Optional.of(sra.getRequest());
        return Optional.empty();
    }
    /** Helper para chamadas simples (proxy). */
    public static Mono<String> proxy(WebClient client, HttpMethod method, String baseUrl, String path,
                                     Map<String, String> query, String body) {
        WebClient.RequestBodySpec req = client.method(method).uri(uriBuilder -> {
            var b = uriBuilder.scheme(null).host(null).path(baseUrl.replaceAll("/$", "") + path);
            if (query != null) query.forEach((k, v) -> { if (v != null) b.queryParam(k, v); });
            return b.build();
        });
        if (body != null) req.contentType(MediaType.APPLICATION_JSON).bodyValue(body);
        return req.retrieve().bodyToMono(String.class);
    }
}
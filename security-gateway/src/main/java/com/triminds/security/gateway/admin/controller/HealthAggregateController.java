package com.triminds.security.gateway.admin.controller;
import com.triminds.security.gateway.admin.config.DownstreamProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/admin/health")
public class HealthAggregateController {
    private final WebClient http;
    private final DownstreamProperties props;
    public HealthAggregateController(WebClient http, DownstreamProperties props) {
        this.http = http;
        this.props = props;
    }
    @GetMapping("/aggregate")
    public ResponseEntity<Map<String, Object>> aggregate() {
        record Target(String name, String baseUrl) {}
        List<Target> targets = List.of(
            new Target("identity", props.getIdentity().getBaseUrl()),
            new Target("access-control", props.getAccess().getBaseUrl()),
            new Target("policy-engine", props.getPolicy().getBaseUrl()),
            new Target("risk-engine", props.getRisk().getBaseUrl()),
            new Target("audit", props.getAudit().getBaseUrl()),
            new Target("intelligence", props.getIntel().getBaseUrl()),
            new Target("auth", props.getAuth().getBaseUrl())
        );
        List<Map<String, Object>> modules = Flux.fromIterable(targets)
            .flatMap(t -> {
                long start = System.currentTimeMillis();
                return http.get()
                    .uri(t.baseUrl().replaceAll("/$", "") + "/actuator/health")
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(3))
                    .map(body -> Map.<String, Object>of(
                        "name", t.name(),
                        "status", body.contains("\"UP\"") ? "UP" : "DOWN",
                        "latencyMs", System.currentTimeMillis() - start))
                    .onErrorReturn(Map.<String, Object>of(
                        "name", t.name(),
                        "status", "DOWN",
                        "latencyMs", System.currentTimeMillis() - start));
            })
            .collectList()
            .blockOptional()
            .orElse(List.of());
        return ResponseEntity.ok(Map.of("modules", modules));
    }
}
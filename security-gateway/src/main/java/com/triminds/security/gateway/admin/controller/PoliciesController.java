package com.triminds.security.gateway.admin.controller;
import com.triminds.security.gateway.admin.client.DownstreamClient;
import com.triminds.security.gateway.admin.config.DownstreamProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
@RestController
@RequestMapping("/admin/policies")
public class PoliciesController {
    private final WebClient http;
    private final String base;
    public PoliciesController(WebClient http, DownstreamProperties props) {
        this.http = http;
        this.base = props.getPolicy().getBaseUrl();
    }
    @GetMapping
    public ResponseEntity<String> list() {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.GET, base, "/policies", null, null).block());
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> get(@PathVariable String id) {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.GET, base, "/policies/" + id, null, null).block());
    }
    @PostMapping
    public ResponseEntity<String> upsert(@RequestBody String body) {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.POST, base, "/policies", null, body).block());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        DownstreamClient.proxy(http, HttpMethod.DELETE, base, "/policies/" + id, null, null).block();
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/evaluate")
    public ResponseEntity<String> evaluate(@RequestBody String body) {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.POST, base, "/policy/evaluate", null, body).block());
    }
}

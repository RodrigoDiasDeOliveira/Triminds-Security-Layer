package com.triminds.security.gateway.admin.controller;
import com.triminds.security.gateway.admin.client.DownstreamClient;
import com.triminds.security.gateway.admin.config.DownstreamProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/admin/identities")
public class IdentitiesController {
    private final WebClient http;
    private final String base;
    public IdentitiesController(WebClient http, DownstreamProperties props) {
        this.http = http;
        this.base = props.getIdentity().getBaseUrl();
    }
    @GetMapping
    public ResponseEntity<String> list(@RequestParam(required = false) String query,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size) {
        Map<String, String> q = new HashMap<>();
        if (query != null) q.put("query", query);
        q.put("page", String.valueOf(page));
        q.put("size", String.valueOf(size));
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.GET, base, "/identities", q, null).block());
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> get(@PathVariable String id) {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.GET, base, "/identities/" + id, null, null).block());
    }
    @PostMapping
    public ResponseEntity<String> create(@RequestBody String body) {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.POST, base, "/identities", null, body).block());
    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody String body) {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.PATCH, base, "/identities/" + id, null, body).block());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable String id) {
        DownstreamClient.proxy(http, HttpMethod.DELETE, base, "/identities/" + id, null, null).block();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/roles")
    public ResponseEntity<String> rolesOf(@PathVariable String id) {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.GET, base, "/identities/" + id + "/roles", null, null).block());
    }
}
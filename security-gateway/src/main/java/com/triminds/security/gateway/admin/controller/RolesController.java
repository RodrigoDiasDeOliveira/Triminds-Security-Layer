package com.triminds.gateway.admin.controller;
import com.triminds.gateway.admin.client.DownstreamClient;
import com.triminds.gateway.admin.config.DownstreamProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
@RestController
@RequestMapping("/admin/roles")
public class RolesController {
    private final WebClient http;
    private final String base;
    public RolesController(WebClient http, DownstreamProperties props) {
        this.http = http;
        this.base = props.getAccess().getBaseUrl();
    }
    @GetMapping
    public ResponseEntity<String> list() {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.GET, base, "/roles", null, null).block());
    }
    @PostMapping("/assign")
    public ResponseEntity<Void> assign(@RequestBody String body) {
        DownstreamClient.proxy(http, HttpMethod.POST, base, "/roles/assign", null, body).block();
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/revoke")
    public ResponseEntity<Void> revoke(@RequestBody String body) {
        DownstreamClient.proxy(http, HttpMethod.POST, base, "/roles/revoke", null, body).block();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/check")
    public ResponseEntity<String> check(@RequestParam String identityId,
                                         @RequestParam String action,
                                         @RequestParam String resource) {
        var q = Map.of("identityId", identityId, "action", action, "resource", resource);
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.GET, base, "/access/check", q, null).block());
    }
}
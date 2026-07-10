package com.triminds.gateway.admin.controller;
import com.triminds.gateway.admin.client.DownstreamClient;
import com.triminds.gateway.admin.config.DownstreamProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
@RestController
public class AuthAdminController {
    private final WebClient http;
    private final String authBase;
    public AuthAdminController(WebClient http, DownstreamProperties props) {
        this.http = http;
        this.authBase = props.getAuth().getBaseUrl();
    }
    /** Passthrough do login para o security-auth. */
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody String body) {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.POST, authBase, "/auth/login", null, body).block());
    }
    @PostMapping("/admin/auth/rotate-keys")
    public ResponseEntity<Void> rotate() {
        DownstreamClient.proxy(http, HttpMethod.POST, authBase, "/auth/keys/rotate", null, null).block();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/admin/auth/sessions")
    public ResponseEntity<String> sessions() {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.GET, authBase, "/auth/sessions", null, null).block());
    }
}
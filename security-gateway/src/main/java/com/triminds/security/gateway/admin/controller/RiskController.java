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
@RequestMapping("/admin/risk")
public class RiskController {
    private final WebClient http;
    private final String base;
    public RiskController(WebClient http, DownstreamProperties props) {
        this.http = http;
        this.base = props.getRisk().getBaseUrl();
    }
    @GetMapping("/signals")
    public ResponseEntity<String> signals(@RequestParam(required = false) String severity,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "50") int size) {
        Map<String, String> q = new HashMap<>();
        if (severity != null) q.put("severity", severity);
        q.put("page", String.valueOf(page));
        q.put("size", String.valueOf(size));
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.GET, base, "/risk/signals", q, null).block());
    }
    @PostMapping("/score")
    public ResponseEntity<String> score(@RequestBody String body) {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.POST, base, "/risk/score", null, body).block());
    }
}

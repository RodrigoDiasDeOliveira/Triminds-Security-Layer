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
@RequestMapping("/admin/audit")
public class AuditController {
    private final WebClient http;
    private final String base;
    public AuditController(WebClient http, DownstreamProperties props) {
        this.http = http;
        this.base = props.getAudit().getBaseUrl();
    }
    @GetMapping
    public ResponseEntity<String> list(@RequestParam String from,
                                        @RequestParam String to,
                                        @RequestParam(required = false) String type,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "50") int size) {
        Map<String, String> q = new HashMap<>();
        q.put("from", from);
        q.put("to", to);
        if (type != null) q.put("type", type);
        q.put("page", String.valueOf(page));
        q.put("size", String.valueOf(size));
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.GET, base, "/audit", q, null).block());
    }
}

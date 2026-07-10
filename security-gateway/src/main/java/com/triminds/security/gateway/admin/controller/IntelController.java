package com.triminds.gateway.admin.controller;
import com.triminds.gateway.admin.client.DownstreamClient;
import com.triminds.gateway.admin.config.DownstreamProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/admin/intel")
public class IntelController {
    private final WebClient http;
    private final String base;
    public IntelController(WebClient http, DownstreamProperties props) {
        this.http = http;
        this.base = props.getIntel().getBaseUrl();
    }
    @GetMapping("/feeds")
    public ResponseEntity<String> feeds() {
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.GET, base, "/intel/feeds", null, null).block());
    }
    @GetMapping("/iocs")
    public ResponseEntity<String> iocs(@RequestParam(required = false) String type,
                                        @RequestParam(required = false) String query,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "50") int size) {
        Map<String, String> q = new HashMap<>();
        if (type != null) q.put("type", type);
        if (query != null) q.put("query", query);
        q.put("page", String.valueOf(page));
        q.put("size", String.valueOf(size));
        return ResponseEntity.ok(DownstreamClient.proxy(http, HttpMethod.GET, base, "/intel/iocs", q, null).block());
    }
}
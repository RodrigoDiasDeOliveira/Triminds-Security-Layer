package com.triminds.security.auth.infrastructure.jwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/.well-known")
public class JwksController {
    private final JwtKeyProvider keys;
    public JwksController(JwtKeyProvider keys) { this.keys = keys; }

    @GetMapping("/jwks.json")
    public Map<String,Object> jwks() { return keys.publicJwks().toJSONObject(); }
}

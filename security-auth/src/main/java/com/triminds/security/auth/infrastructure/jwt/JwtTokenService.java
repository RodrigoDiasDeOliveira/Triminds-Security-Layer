package com.triminds.security.auth.infrastructure.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.triminds.security.auth.application.ports.TokenIssuerPort;
import com.triminds.security.auth.domain.AuthSession;
import com.triminds.security.auth.domain.TokenPair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class JwtTokenService implements TokenIssuerPort {

    private final JwtKeyProvider keys;
    private final String issuer;
    private final String audience;
    private final long ttlSeconds;

    public JwtTokenService(JwtKeyProvider keys,
                           @Value("${triminds.auth.jwt.issuer:https://triminds.com}") String issuer,
                           @Value("${triminds.auth.jwt.audience:triminds-api}") String audience,
                           @Value("${triminds.auth.jwt.ttl-seconds:900}") long ttlSeconds) {
        this.keys = keys; this.issuer = issuer; this.audience = audience; this.ttlSeconds = ttlSeconds;
    }

    @Override
    public TokenPair issue(AuthSession s) {
        try {
            Date now = new Date();
            Date exp = new Date(now.getTime() + ttlSeconds * 1000);
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .issuer(issuer).audience(audience).subject(s.userId())
                    .jwtID(UUID.randomUUID().toString())
                    .issueTime(now).expirationTime(exp)
                    .claim("tenant_id", s.tenantId())
                    .claim("session_id", s.sessionId())
                    .claim("roles", s.roles())
                    .claim("scope", String.join(" ", s.scopes()))
                    .build();
            SignedJWT jwt = new SignedJWT(
                    new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(keys.current().getKeyID()).build(),
                    claims);
            jwt.sign(new RSASSASigner(keys.current()));
            String refresh = Base64.getUrlEncoder().withoutPadding().encodeToString(
                    (UUID.randomUUID().toString() + UUID.randomUUID()).getBytes(StandardCharsets.UTF_8));
            return new TokenPair(jwt.serialize(), refresh, ttlSeconds, "Bearer");
        } catch (JOSEException e) { throw new IllegalStateException("Failed to sign JWT", e); }
    }

    @Override
    public String hashRefresh(String refresh) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(refresh.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) { throw new IllegalStateException(e); }
    }
}


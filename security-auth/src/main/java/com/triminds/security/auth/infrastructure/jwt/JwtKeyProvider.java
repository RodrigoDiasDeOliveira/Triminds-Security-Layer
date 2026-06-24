package com.triminds.auth.infrastructure.jwt;

import com.triminds.auth.Jwt.key;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Component
public class JwtKeyProvider {
    private final RSAKey rsaJwk;

    public JwtKeyProvider(@Value("${triminds.auth.jwt.key-id:#{null}}") String keyId) throws Exception {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair kp = gen.generateKeyPair();
        this.rsaJwk = new RSAKey.Builder((RSAPublicKey) kp.getPublic())
                .privateKey((RSAPrivateKey) kp.getPrivate())
                .keyID(keyId != null ? keyId : UUID.randomUUID().toString())
                .build();
    }
    public RSAKey current() { return rsaJwk; }
    public JWKSet publicJwks() { return new JWKSet(rsaJwk.toPublicJWK()); }
}

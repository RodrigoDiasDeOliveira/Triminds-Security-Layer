package com.triminds.auth.infrastructure.config;

import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.triminds.auth.infrastructure.jwt.JwtKeyProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtDecoder jwtDecoder(JwtKeyProvider keys) throws Exception {
        return NimbusJwtDecoder.withPublicKey(keys.current().toRSAPublicKey()).build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(c -> c.disable())
            .authorizeHttpRequests(a -> a
                .requestMatchers("/api/v1/auth/**", "/.well-known/**", "/actuator/health", "/actuator/info").permitAll()
                .anyRequest().authenticated())
            .oauth2ResourceServer(o -> o.jwt(j -> {}));
        return http.build();
    }
}

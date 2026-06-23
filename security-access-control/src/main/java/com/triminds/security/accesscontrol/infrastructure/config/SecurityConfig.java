package com.triminds.security.accesscontrol.infrastructure.config;

import com.triminds.security.shared.web.SecurityHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.UUID;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(c -> c.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(a -> a
                .requestMatchers("/actuator/health/**", "/actuator/info", "/actuator/prometheus",
                                 "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated())
            .oauth2ResourceServer(o -> o.jwt(j -> {}))
            .addFilterAfter(tenantFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /** Garante que X-Tenant-Id === claim 'tid' do JWT (anti-spoof). */
    @Bean
    public OncePerRequestFilter tenantFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
                    throws java.io.IOException, jakarta.servlet.ServletException {
                String header = req.getHeader(SecurityHeaders.TENANT_ID);
                var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.getPrincipal() instanceof Jwt jwt && header != null) {
                    String tid = jwt.getClaimAsString("tid");
                    if (tid == null || !tid.equals(header)) {
                        res.sendError(HttpServletResponse.SC_FORBIDDEN, "tenant mismatch");
                        return;
                    }
                    try { UUID.fromString(header); } catch (Exception e) {
                        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid tenant id");
                        return;
                    }
                }
                chain.doFilter(req, res);
            }
        };
    }
}

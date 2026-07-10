package com.triminds.gateway.admin.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.UUID;
/**
 * Lê X-Tenant-Id / X-Request-Id e coloca em MDC + request attributes.
 */
@Component
@Order(1)
public class TenantFilter extends OncePerRequestFilter {
    public static final String TENANT_HEADER = "X-Tenant-Id";
    public static final String REQUEST_ID_HEADER = "X-Request-Id";
    public static final String TENANT_ATTR = "triminds.tenantId";
    public static final String REQUEST_ID_ATTR = "triminds.requestId";
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String tenant = req.getHeader(TENANT_HEADER);
        String requestId = req.getHeader(REQUEST_ID_HEADER);
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }
        try {
            if (tenant != null && !tenant.isBlank()) {
                MDC.put("tenant", tenant);
                req.setAttribute(TENANT_ATTR, tenant);
            }
            MDC.put("requestId", requestId);
            req.setAttribute(REQUEST_ID_ATTR, requestId);
            res.setHeader(REQUEST_ID_HEADER, requestId);
            chain.doFilter(req, res);
        } finally {
            MDC.remove("tenant");
            MDC.remove("requestId");
        }
    }
}
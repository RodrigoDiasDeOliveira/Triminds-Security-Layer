package com.triminds.security.shared.web;

import com.triminds.security.shared.tenant.TenantContext;
import com.triminds.security.shared.tenant.TenantHolder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import java.io.IOException;

public class TenantResolverFilter implements Filter {
    public static final String HEADER = "X-Tenant-Id";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest http = (HttpServletRequest) req;
        String tenant = http.getHeader(HEADER);
        String cid = MDC.get(CorrelationIdFilter.MDC_KEY);
        if (tenant != null && !tenant.isBlank()) {
            TenantHolder.set(TenantContext.of(tenant, cid));
            MDC.put("tenantId", tenant);
        }
        try { chain.doFilter(req, res); } finally {
            TenantHolder.clear(); MDC.remove("tenantId");
        }
    }
}
package com.triminds.shared.web;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import java.io.IOException;
import java.util.UUID;

public class CorrelationIdFilter implements Filter {
    public static final String HEADER = "X-Correlation-Id";
    public static final String MDC_KEY = "correlationId";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest http = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;
        String cid = http.getHeader(HEADER);
        if (cid == null || cid.isBlank()) cid = UUID.randomUUID().toString();
        MDC.put(MDC_KEY, cid);
        httpRes.setHeader(HEADER, cid);
        try { chain.doFilter(req, res); } finally { MDC.remove(MDC_KEY); }
    }
}

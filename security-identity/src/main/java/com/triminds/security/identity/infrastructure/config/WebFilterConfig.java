package com.triminds.identity.infrastructure.config;

import com.triminds.shared.web.CorrelationIdFilter;
import com.triminds.shared.web.TenantResolverFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFiltersConfig {
    @Bean FilterRegistrationBean<CorrelationIdFilter> correlationId() {
        var b = new FilterRegistrationBean<>(new CorrelationIdFilter()); b.setOrder(1); return b;
    }
    @Bean FilterRegistrationBean<TenantResolverFilter> tenant() {
        var b = new FilterRegistrationBean<>(new TenantResolverFilter()); b.setOrder(2); return b;
    }
}

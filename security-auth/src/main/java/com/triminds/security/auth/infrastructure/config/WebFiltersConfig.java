<<<<<<< HEAD:security-auth/src/main/java/com/triminds/security/auth/infrastructure/config/WebFiltersConfig.java
package com.triminds.security.auth.infrastructure.config;
=======
package com.triminds.security.identity.infrastructure.config;
>>>>>>> 38ec414 (update commit):security-identity/src/main/java/com/triminds/security/identity/infrastructure/config/WebFilterConfig.java

import com.triminds.security.shared.web.CorrelationIdFilter;
import com.triminds.security.shared.web.TenantResolverFilter;
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

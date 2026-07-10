package com.triminds.security.gateway.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "triminds.downstream")
public class DownstreamProperties {
    private Endpoint identity = new Endpoint();
    private Endpoint access = new Endpoint();
    private Endpoint policy = new Endpoint();
    private Endpoint risk = new Endpoint();
    private Endpoint audit = new Endpoint();
    private Endpoint intel = new Endpoint();
    private Endpoint auth = new Endpoint();
    public static class Endpoint {
        private String baseUrl;
        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String v) { this.baseUrl = v; }
    }
    public Endpoint getIdentity() { return identity; }
    public Endpoint getAccess() { return access; }
    public Endpoint getPolicy() { return policy; }
    public Endpoint getRisk() { return risk; }
    public Endpoint getAudit() { return audit; }
    public Endpoint getIntel() { return intel; }
    public Endpoint getAuth() { return auth; }
    public void setIdentity(Endpoint v) { this.identity = v; }
    public void setAccess(Endpoint v) { this.access = v; }
    public void setPolicy(Endpoint v) { this.policy = v; }
    public void setRisk(Endpoint v) { this.risk = v; }
    public void setAudit(Endpoint v) { this.audit = v; }
    public void setIntel(Endpoint v) { this.intel = v; }
    public void setAuth(Endpoint v) { this.auth = v; }
}
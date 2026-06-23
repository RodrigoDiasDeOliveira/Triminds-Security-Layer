package com.triminds.security.policy.application.port;

public interface OpaSyncPort {
    /** Push de uma versão Rego para o OPA sob o path triminds/tenants/{tenant}/{policy}. */
    void pushPolicy(String path, String rego);
    void deletePolicy(String path);
}

package com.triminds.security.policy.infrastructure.opa;

import com.triminds.security.policy.application.port.OpaSyncPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpaSyncAdapter implements OpaSyncPort {
    private final OpaClient opa;
    @Override public void pushPolicy(String path, String rego) {
        opa.putPolicy(path.replace('/', '.'), rego);
    }
    @Override public void deletePolicy(String path) {
        opa.deletePolicy(path.replace('/', '.'));
    }
}

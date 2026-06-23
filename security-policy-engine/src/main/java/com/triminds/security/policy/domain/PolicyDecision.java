package com.triminds.security.policy.domain;

import java.util.List;

public record PolicyDecision(boolean allow, List<String> reasons, List<String> obligations) {
    public static PolicyDecision deny(String reason) { return new PolicyDecision(false, List.of(reason), List.of()); }
    public static PolicyDecision allow()             { return new PolicyDecision(true, List.of(), List.of()); }
}

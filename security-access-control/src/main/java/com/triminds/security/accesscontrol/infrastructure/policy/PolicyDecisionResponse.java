package com.triminds.security.accesscontrol.infrastructure.policy;

import java.util.List;

public record PolicyDecisionResponse(boolean allow, List<String> reasons, List<String> obligations) {}

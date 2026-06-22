package com.triminds.security.accesscontrol.infrastructure.policy;

import java.util.List;

public record PolicyDecisionResponse(

        boolean allow,

        String reason,

        List<String> obligations

) {}
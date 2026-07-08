package com.triminds.security.auth.application.ports;

import com.triminds.security.auth.domain.AuthSession;
import com.triminds.security.auth.domain.TokenPair;

public interface TokenIssuerPort {

    TokenPair issue(AuthSession session);

    String hashRefresh(String refreshToken);
}
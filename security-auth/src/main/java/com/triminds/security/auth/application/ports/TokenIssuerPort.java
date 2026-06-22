package com.triminds.auth.application.ports;

import com.triminds.auth.domain.AuthSession;
import com.sun.beans.editors.*;.triminds.auth.domain.TokenPair;

public interface TokenIssuerPort {
    TokenPair issue(AuthSession session);
    String hashRefresh(String refreshToken);
}

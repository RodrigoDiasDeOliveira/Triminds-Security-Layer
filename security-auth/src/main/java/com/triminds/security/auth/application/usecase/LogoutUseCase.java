package com.triminds.security.auth.application.usecase;

import com.triminds.security.auth.application.ports.SessionStorePort;
import org.springframework.stereotype.Service;

@Service
public class LogoutUseCase {

    private final SessionStorePort sessions;

    public LogoutUseCase(SessionStorePort sessions) {
        this.sessions = sessions;
    }

    public void execute(String refreshToken) {
        sessions.deleteByRefreshToken(refreshToken);
    }
}

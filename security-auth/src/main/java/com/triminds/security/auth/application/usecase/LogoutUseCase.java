package com.triminds.auth.application.usecase;

import com.triminds.auth.application.ports.SessionStorePort;
import org.springframework.stereotype.Service;

@Service
public class LogoutUseCase {
    private final SessionStorePort sessions;
    public LogoutUseCase(SessionStorePort sessions) { this.sessions = sessions; }
    public void execute(String sessionId) { sessions.revoke(sessionId); }
}

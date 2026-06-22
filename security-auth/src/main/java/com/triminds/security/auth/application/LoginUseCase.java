package com.triminds.security.auth.application.usecase;

import com.triminds.security.auth.AuthResponse;

public interface LoginUseCase {
    AuthResponse execute(String email, String password);
}
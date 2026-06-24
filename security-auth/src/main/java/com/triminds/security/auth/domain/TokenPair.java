package com.triminds.security.auth.domain;


public record TokenPair(String accessToken, String refreshToken, long expiresInSeconds, String tokenType) {}

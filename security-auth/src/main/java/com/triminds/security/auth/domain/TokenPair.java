package io.triminds.auth.domain;


public record TokenPair(String accessToken, String refreshToken, long expiresInSeconds, String tokenType) {}

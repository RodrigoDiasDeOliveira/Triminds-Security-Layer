package com.triminds.security.identity.infrastructure.web.dto;
public record LockRequest(String reason, long lockMinutes) {}

package com.triminds.security.identity.application.usecase;
public class IdentityNotFoundException extends RuntimeException {
    public IdentityNotFoundException(String message) { super(message); }
}

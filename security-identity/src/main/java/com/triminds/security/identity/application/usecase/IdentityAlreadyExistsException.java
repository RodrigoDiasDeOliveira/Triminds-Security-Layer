package com.triminds.security.identity.application.usecase;
public class IdentityAlreadyExistsException extends RuntimeException {
    public IdentityAlreadyExistsException(String message) { super(message); }
}

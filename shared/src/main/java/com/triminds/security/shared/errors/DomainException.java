package com.triminds.shared.errors;

public class DomainException extends RuntimeException {
    private final String code;
    public DomainException(String code, String message) { super(message); this.code = code; }
    public String getCode() { return code; }
}

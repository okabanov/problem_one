package com.okabanov.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("User not authorized");
    }
}

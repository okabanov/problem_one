package com.okabanov.exception;

public class UnsupportedCommandException extends RuntimeException {
    public UnsupportedCommandException() {
        super("Unsupported command");
    }
}

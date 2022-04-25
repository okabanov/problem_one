package com.okabanov.atm.exception;

public class UnsupportedCommandException extends RuntimeException {
    public UnsupportedCommandException() {
        super("Unsupported command");
    }
}

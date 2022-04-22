package com.okabanov.exception;

public class ShellMethodNotFound extends RuntimeException {
    public ShellMethodNotFound() {
        super("Shell command not found");
    }
}
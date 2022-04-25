package com.okabanov.atm.exception;

public class IncorrectArgumentsCountException extends RuntimeException {
    public IncorrectArgumentsCountException(int expected, int found) {
        super(String.format("Incorrect arguments count (expected: %d, found: %d)", expected, found));
    }
}

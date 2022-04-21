package com.okabanov.parser;

public enum Action {
    LOGIN   (1),
    DEPOSIT (1),
    WITHDRAW(1),
    TRANSFER(2),
    LOGOUT  (0);
    private int countArguments;

    private Action(int countArguments) {
        this.countArguments = countArguments;
    }

    public int getCountArguments() {
        return countArguments;
    }
}

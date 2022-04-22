package com.okabanov.exception;

public class NotEnoughMoney extends RuntimeException {
    public NotEnoughMoney() {
        super("Not enough money");
    }
}
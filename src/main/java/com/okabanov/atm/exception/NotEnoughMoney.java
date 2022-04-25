package com.okabanov.atm.exception;

public class NotEnoughMoney extends RuntimeException {
    public NotEnoughMoney() {
        super("Not enough money");
    }
}
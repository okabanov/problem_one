package com.okabanov.atm.exception;

import com.okabanov.atm.i18n.I18n;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException() {
        super(I18n.notEnoughMoneyMessage());
    }
}
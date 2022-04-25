package com.okabanov.atm.exception;

import com.okabanov.atm.i18n.I18n;

public class UnsupportedCommandException extends RuntimeException {
    public UnsupportedCommandException() {
        super(I18n.unsupportedCommandMessage());
    }
}

package com.okabanov.atm.exception;

import com.okabanov.atm.i18n.I18n;

public class IncorrectArgumentsCountException extends RuntimeException {
    public IncorrectArgumentsCountException(int expected, int found) {
        super(I18n.incorrectArgumentCountMessage(expected, found));
    }
}

package com.okabanov.atm.exception;

import com.okabanov.atm.i18n.I18n;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super(I18n.useNotAuthorizedMessage());
    }
}

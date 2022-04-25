package com.okabanov.atm.shell;

import com.okabanov.atm.exception.UnauthorizedException;
import com.okabanov.atm.i18n.I18n;

import java.util.HashMap;

public class LogoutMethod extends ShellMethod {
    public LogoutMethod(HashMap<String, String> userState) {
        super(userState);
    }

    @Override
    public int getCountArguments() {
        return 0;
    }

    @Override
    public String getMethodName() {
        return "logout";
    }

    @Override
    public String executeMethod(String command, String[] args) {
        String userLogin = getCurrentUser();
        if (getCurrentUser() == null)
            throw new UnauthorizedException();
        setCurrentUser(null);
        return I18n.goodbyeMessage(userLogin);
    }
}

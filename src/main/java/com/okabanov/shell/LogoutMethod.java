package com.okabanov.shell;

import com.okabanov.exception.UnauthorizedException;

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
        return String.format("Goodbye, %s!\n", userLogin);
    }
}

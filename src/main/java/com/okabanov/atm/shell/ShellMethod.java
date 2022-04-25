package com.okabanov.atm.shell;

import com.okabanov.dto.BalanceDto;
import com.okabanov.dto.DebtDto;

import java.util.HashMap;

abstract public class ShellMethod {
    static final String CURRENT_USER_STATE_KEY = "currentUser";

    private final HashMap<String, String> userState;

    public ShellMethod(HashMap<String, String> userState) {
        this.userState = userState;
    }

    abstract public int getCountArguments();

    abstract public String getMethodName();

    abstract public String executeMethod(String command, String[] args);

    protected String getCurrentUser() {
        return userState.get(CURRENT_USER_STATE_KEY);
    }

    protected void setCurrentUser(String userLogin) {
        userState.put(CURRENT_USER_STATE_KEY, userLogin);
    }
}

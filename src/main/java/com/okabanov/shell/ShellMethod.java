package com.okabanov.shell;

import java.util.HashMap;

abstract public class ShellMethod {
    static final String CURRENT_USER_STATE_KEY = "currentUser";

    private HashMap<String, Object> userState;

    public ShellMethod(HashMap<String, Object> userState) {
        this.userState = userState;
    }

    abstract public int getCountArguments();
    abstract public String getMethodName();
    abstract public String executeMethod(String command, String[] args);

    protected String getCurrentUser() {
        Object state = userState.get(CURRENT_USER_STATE_KEY);
        if (state == null)
            return null;
        return state.toString();
    }

    protected void setCurrentUser(String userLogin) {
        userState.put(CURRENT_USER_STATE_KEY, userLogin);;
    }
}

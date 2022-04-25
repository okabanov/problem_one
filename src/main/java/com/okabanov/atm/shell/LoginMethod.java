package com.okabanov.atm.shell;

import com.okabanov.server.ServerRPC;

import java.util.HashMap;

public class LoginMethod extends ShellMethod {
    private final ServerRPC serverRPC;

    public LoginMethod(HashMap<String, String> userState, ServerRPC serverRPC) {
        super(userState);
        this.serverRPC = serverRPC;
    }

    @Override
    public int getCountArguments() {
        return 1;
    }

    @Override
    public String getMethodName() {
        return "login";
    }

    @Override
    public String executeMethod(String command, String[] args) {
        String login = args[0];
        String token = serverRPC.loginUser(login);
        setCurrentUser(token);
        return String.format("Hello, %s!\n%s", login, buildBalanceMessage(serverRPC.balance(token)));
    }
}

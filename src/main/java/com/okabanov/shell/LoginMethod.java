package com.okabanov.shell;

import com.okabanov.model.User;
import com.okabanov.service.BalanceService;
import com.okabanov.service.UserService;

import java.util.HashMap;

public class LoginMethod extends ShellMethod {
    private UserService userService;
    private BalanceService balanceService;

    public LoginMethod(HashMap<String, Object> userState, UserService userService, BalanceService balanceService) {
        super(userState);
        this.userService = userService;
        this.balanceService = balanceService;
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
        if (userService.findByLogin(login) == null) {
            userService.createUser(login);
        }
        setCurrentUser(login);
        return String.format("Hello, %s!\n%s", login, balanceService.buildBalanceMessage(getCurrentUser()));
    }
}

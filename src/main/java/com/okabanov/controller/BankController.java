package com.okabanov.controller;

import com.okabanov.model.User;
import com.okabanov.service.UserService;

public class BankController {
    private UserService userService = new UserService();

    private String currentUserLogin;

    public void login(String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        currentUserLogin = login;
        System.out.printf("Hello, %s!\nYour balance is %d\n", user.getLogin(), user.getBalance());
    }

    public void deposit(String amount) {
        int amountInt = Integer.parseInt(amount);
        if (currentUserLogin == null) {
            throw new IllegalArgumentException("Unauthorized exception");
        }
        User user = userService.findByLogin(currentUserLogin);
        user.increaseBalance(amountInt);

        System.out.printf("Your balance is %d\n", user.getBalance());
    }


}

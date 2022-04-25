package com.okabanov.server.service;

import com.okabanov.server.model.User;

import java.util.ArrayList;

public class UserService {
    private final ArrayList<User> usersState = new ArrayList<User>();

    public User createUser(String login) {
        User newUser = new User(login, 0);
        usersState.add(newUser);
        return newUser;
    }

    public User findByLogin(String login) {
        return usersState.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findAny()
                .orElse(null);
    }

    public void increaseBalance(String login, int amount) {
        findByLogin(login).increaseBalance(amount);
    }

    public void decreaseBalance(String login, int amount) {
        findByLogin(login).decreaseBalance(amount);
    }
}

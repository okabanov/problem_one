package com.okabanov.service;

import com.okabanov.model.User;

import java.util.ArrayList;

public class UserService {
    private ArrayList<User> usersState = new ArrayList<User>();

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
}

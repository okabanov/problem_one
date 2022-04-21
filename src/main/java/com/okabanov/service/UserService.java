package com.okabanov.service;

import com.okabanov.model.User;

import java.util.ArrayList;

public class UserService {
    private ArrayList<User> usersState = new ArrayList<User>();

    public UserService() {
        initTestUsers();
    }

    public User findByLogin(String login) {
        return usersState.stream()
                .filter(customer -> login.equals(customer.getLogin()))
                .findAny()
                .orElse(null);
    }

    public User changeBalance(String login) {
        return usersState.stream()
                .filter(customer -> login.equals(customer.getLogin()))
                .findAny()
                .orElse(null);
    }

    private void initTestUsers() {
        usersState.add(new User(1, "Alice", 0));
        usersState.add(new User(2, "Bob", 0));
    }
}

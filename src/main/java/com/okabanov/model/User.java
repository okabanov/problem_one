package com.okabanov.model;

public class User {
    private String login;
    private int balance;

    public User(String login, int balance) {
        this.login = login;
        this.balance = balance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int increaseBalance(int increaseVal) {
        balance = balance + increaseVal;
        return balance;
    }

    public int decreaseBalance(int decreaseVal) {
        balance = balance - decreaseVal;
        return balance;
    }
}

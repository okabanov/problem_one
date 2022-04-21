package com.okabanov.model;

public class User {
    private int id;
    private String login;
    private int balance;

    public User(int id, String login, int balance) {
        this.id = id;
        this.login = login;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int increaseBalance(int increaseVal) {
        balance = balance + increaseVal;
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}

package com.okabanov.server.model;

public class Debt {
    private final String debtor;
    private final String borrower;
    private int amount;

    public Debt(String debtor, String borrower, int amount) {
        this.debtor = debtor;
        this.borrower = borrower;
        this.amount = amount;
    }

    public String getDebtor() {
        return debtor;
    }

    public String getBorrower() {
        return borrower;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void increaseAmount(int amount) {
        this.amount += amount;
    }

    public void decreaseAmount(int amount) {
        this.amount -= amount;
    }
}

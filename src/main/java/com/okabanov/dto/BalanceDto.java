package com.okabanov.dto;

public class BalanceDto {
    private int balance;
    private DebtDto owedTo;
    private DebtDto owedFrom;

    public BalanceDto(int balance, DebtDto owedTo, DebtDto owedFrom) {
        this.balance = balance;
        this.owedTo = owedTo;
        this.owedFrom = owedFrom;
    }

    public BalanceDto(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public DebtDto getOwedTo() {
        return owedTo;
    }

    public void setOwedTo(DebtDto owedTo) {
        this.owedTo = owedTo;
    }

    public DebtDto getOwedFrom() {
        return owedFrom;
    }

    public void setOwedFrom(DebtDto owedFrom) {
        this.owedFrom = owedFrom;
    }
}

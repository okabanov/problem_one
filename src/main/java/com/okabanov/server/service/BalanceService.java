package com.okabanov.server.service;

import com.okabanov.dto.BalanceDto;
import com.okabanov.dto.DebtDto;
import com.okabanov.server.model.Debt;
import com.okabanov.server.model.User;

public class BalanceService {
    private final DebtService debtService;
    private final UserService userService;

    public BalanceService(DebtService debtService, UserService userService) {
        this.debtService = debtService;
        this.userService = userService;
    }

    public BalanceDto buildBalance(String userLogin) {
        Debt debtor = debtService.findByDebtor(userLogin);
        Debt borrower = debtService.findByBorrower(userLogin);
        User user = userService.findByLogin(userLogin);

        BalanceDto balance = new BalanceDto(user.getBalance());
        if (debtor != null) {
            balance.setOwedTo(new DebtDto(debtor.getAmount(), debtor.getBorrower()));
        }
        if (borrower != null) {
            balance.setOwedFrom(new DebtDto(borrower.getAmount(), borrower.getDebtor()));
        }
        return balance;
    }
}

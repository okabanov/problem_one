package com.okabanov.service;

import com.okabanov.model.Debt;
import com.okabanov.model.User;

public class BalanceService {
    private DebtService debtService;
    private UserService userService;

    public BalanceService(DebtService debtService, UserService userService) {
        this.debtService = debtService;
        this.userService = userService;
    }

    public String buildBalanceMessage(String userLogin) {
        Debt debtor = debtService.findByDebtor(userLogin);
        Debt borrower = debtService.findByBorrower(userLogin);
        User user = userService.findByLogin(userLogin);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Your balance is $%d\n", user.getBalance()));
        if (debtor != null)
            stringBuilder.append(String.format("Owed $%d to %s\n", debtor.getAmount(), debtor.getBorrower()));
        if (borrower != null)
            stringBuilder.append(String.format("Owed $%d from %s\n", borrower.getAmount(), borrower.getDebtor()));
        return stringBuilder.toString();
    }
}

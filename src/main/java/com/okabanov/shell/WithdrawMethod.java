package com.okabanov.shell;

import com.okabanov.exception.NotEnoughMoney;
import com.okabanov.exception.UnauthorizedException;
import com.okabanov.model.Debt;
import com.okabanov.service.BalanceService;
import com.okabanov.service.DebtService;
import com.okabanov.service.UserService;

import java.util.HashMap;

public class WithdrawMethod extends ShellMethod {
    private UserService userService;
    private BalanceService balanceService;

    public WithdrawMethod(
            HashMap<String, Object> userState,
            UserService userService,
            BalanceService balanceService
    ) {
        super(userState);
        this.userService = userService;
        this.balanceService = balanceService;
    }

    @Override
    public int getCountArguments() {
        return 1;
    }

    @Override
    public String getMethodName() {
        return "withdraw";
    }

    @Override
    public String executeMethod(String command, String[] args) {
        int amount = Integer.parseInt(args[0]);
        if (userService.findByLogin(getCurrentUser()).getBalance() < amount)
            throw new NotEnoughMoney();
        userService.decreaseBalance(getCurrentUser(), amount);
        return String.format("Withdrawn $%d\n%s", amount, balanceService.buildBalanceMessage(getCurrentUser()));
    }
}

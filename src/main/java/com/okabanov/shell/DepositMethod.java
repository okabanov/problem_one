package com.okabanov.shell;

import com.okabanov.exception.UnauthorizedException;
import com.okabanov.model.Debt;
import com.okabanov.service.BalanceService;
import com.okabanov.service.DebtService;
import com.okabanov.service.UserService;

import java.util.HashMap;

public class DepositMethod extends ShellMethod {
    private UserService userService;
    private DebtService debtService;
    private BalanceService balanceService;

    public DepositMethod(
            HashMap<String, Object> userState,
            UserService userService,
            DebtService debtService,
            BalanceService balanceService
    ) {
        super(userState);
        this.userService = userService;
        this.debtService = debtService;
        this.balanceService = balanceService;
    }

    @Override
    public int getCountArguments() {
        return 1;
    }

    @Override
    public String getMethodName() {
        return "deposit";
    }

    @Override
    public String executeMethod(String command, String[] args) {
        int amount = Integer.parseInt(args[0]);
        String currentUserLogin = getCurrentUser();

        if (currentUserLogin == null) {
            throw new UnauthorizedException();
        }
        Debt debt = debtService.findByDebtor(currentUserLogin);
        String transferMessage = "";

        int remainingAmount = amount;
        if (debt != null) {
            int decreasedAmount = debtService.decreaseDebtAndReturnDecreasedAmount(currentUserLogin, debt.getBorrower(), amount);
            userService.findByLogin(debt.getBorrower()).increaseBalance(decreasedAmount);
            transferMessage = String.format("Transferred $%d to %s\n", decreasedAmount, debt.getBorrower());
            remainingAmount = amount - decreasedAmount;
        }
        if (remainingAmount > 0) {
            userService.increaseBalance(currentUserLogin, remainingAmount);
        }

        return transferMessage + balanceService.buildBalanceMessage(getCurrentUser());
    }
}

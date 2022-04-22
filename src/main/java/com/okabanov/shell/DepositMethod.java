package com.okabanov.shell;

import com.okabanov.exception.UnauthorizedException;
import com.okabanov.model.Debt;
import com.okabanov.model.User;
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
        User user = userService.findByLogin(currentUserLogin);
        Debt debt = debtService.findByDebtor(currentUserLogin);
        String transferMessage = "";
        if (debt == null) {
            user.increaseBalance(amount);
        } else if (amount < debt.getAmount()) {
            debt.decreaseAmount(amount);
            userService.findByLogin(debt.getBorrower()).increaseBalance(amount);
            transferMessage = String.format("Transferred $%d to %s\n", amount, debt.getBorrower());
        } else {
            debtService.deleteDebtor(currentUserLogin);
            int extraAmount = amount - debt.getAmount();
            user.increaseBalance(extraAmount);
            userService.findByLogin(debt.getBorrower()).increaseBalance(amount - extraAmount);
            transferMessage = String.format("Transferred $%d to %s\n", amount - extraAmount, debt.getBorrower());
        }

        return transferMessage + balanceService.buildBalanceMessage(getCurrentUser());
    }
}

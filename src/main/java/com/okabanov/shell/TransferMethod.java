package com.okabanov.shell;

import com.okabanov.exception.UnauthorizedException;
import com.okabanov.model.Debt;
import com.okabanov.model.User;
import com.okabanov.service.BalanceService;
import com.okabanov.service.DebtService;
import com.okabanov.service.UserService;

import java.util.HashMap;

public class TransferMethod extends ShellMethod {
    private UserService userService;
    private DebtService debtService;
    private BalanceService balanceService;

    public TransferMethod(
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
        return 2;
    }

    @Override
    public String getMethodName() {
        return "transfer";
    }

    @Override
    public String executeMethod(String command, String[] args) {
        int amount = Integer.parseInt(args[1]);
        String targetUserLogin = args[0];

        String currentUserLogin = getCurrentUser();
        if (currentUserLogin == null) {
            throw new UnauthorizedException();
        }

        Debt debtFrom = debtService.findByUsers(currentUserLogin, targetUserLogin);
        Debt debtTo = debtService.findByUsers(targetUserLogin, currentUserLogin);
        int currentUserBalance = userService.findByLogin(currentUserLogin).getBalance();

        int transferredAmount = 0;
        if (debtFrom != null) { // Current user debtor
            debtService.increaseDebt(targetUserLogin, currentUserLogin, amount);
        } else if(debtTo != null) {  // Target user debtor
            if (debtTo.getAmount() <= amount) {
                int decreaseAmount = debtService
                        .decreaseDebtAndReturnDecreasedAmount(currentUserLogin, targetUserLogin, amount);
                userService.decreaseBalance(currentUserLogin, amount - debtTo.getAmount());
                userService.increaseBalance(targetUserLogin, amount - debtTo.getAmount());
                transferredAmount = amount;
            } else {
                debtService.decreaseDebtAndReturnDecreasedAmount(targetUserLogin, currentUserLogin, amount);
            }
        } else {
            if (currentUserBalance >= amount) {
                userService.decreaseBalance(currentUserLogin, amount);
                userService.increaseBalance(targetUserLogin, amount);
                transferredAmount = amount;
            } else {
                int unavailableAmount = amount - currentUserBalance;
                int availableAmount = amount - unavailableAmount;
                debtService.increaseDebt(currentUserLogin, targetUserLogin, unavailableAmount);
                userService.decreaseBalance(currentUserLogin, availableAmount);
                userService.increaseBalance(targetUserLogin, availableAmount);
                transferredAmount = availableAmount;
            }
        }

        String transferredMessage = "";
        if (transferredAmount > 0) {
            transferredMessage += String.format("Transferred $%d to %s\n", transferredAmount, targetUserLogin);
        }

        return transferredMessage + balanceService.buildBalanceMessage(currentUserLogin);
    }
}

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

        User currentUser = userService.findByLogin(currentUserLogin);
        User targetUser = userService.findByLogin(targetUserLogin);
        Debt debtTo = debtService.findByUsers(targetUserLogin, currentUserLogin);
        int transferredAmount;
        if (debtTo != null) {
            if (debtTo.getAmount() == amount) {
                transferredAmount = amount;
                debtService.deleteDebtor(targetUserLogin);
            } else if (debtTo.getAmount() < amount) {
                debtService.deleteDebtor(targetUserLogin);
                currentUser.decreaseBalance(amount - debtTo.getAmount());
                targetUser.increaseBalance(amount - debtTo.getAmount());
                transferredAmount = amount;
            } else {
                transferredAmount = 0;
                debtTo.decreaseAmount(amount);
            }
        } else if (currentUser.getBalance() >= amount) {
            currentUser.decreaseBalance(amount);
            targetUser.increaseBalance(amount);
            transferredAmount = amount;
        } else {
            int unavailableAmount = amount - currentUser.getBalance();
            int availableAmount = amount - unavailableAmount;
            Debt debt = debtService.findByUsers(currentUserLogin, targetUserLogin);
            if (debt != null) {
                debt.increaseAmount(unavailableAmount);
            } else {
                debtService.createDebt(currentUserLogin, targetUserLogin, unavailableAmount);
            }
            targetUser.increaseBalance(availableAmount);
            currentUser.decreaseBalance(availableAmount);
            transferredAmount = availableAmount;
        }

        String transferredMessage = "";
        if (transferredAmount > 0) {
            transferredMessage += String.format("Transferred $%d to %s\n", transferredAmount, targetUserLogin);
        }

        return transferredMessage + balanceService.buildBalanceMessage(currentUserLogin);
    }
}

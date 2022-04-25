package com.okabanov.server;

import com.okabanov.dto.BalanceDto;
import com.okabanov.dto.TransferDto;
import com.okabanov.server.model.Debt;
import com.okabanov.server.service.BalanceService;
import com.okabanov.server.service.DebtService;
import com.okabanov.server.service.UserService;
import com.okabanov.server.transaction.Transactional;

public class ServerRPC {
    private final UserService userService;
    private final DebtService debtService;
    private final BalanceService balanceService;

    public ServerRPC(UserService userService, DebtService debtService, BalanceService balanceService) {
        this.userService = userService;
        this.debtService = debtService;
        this.balanceService = balanceService;
    }

    @Transactional
    public String loginUser(String login) {
        if (userService.findByLogin(login) == null) {
            userService.createUser(login);
        }
        return login; // it should be token with ttl control
    }

    @Transactional
    public BalanceDto balance(String login) {
        return balanceService.buildBalance(login);
    }

    @Transactional
    public int transfer(String currentUserLogin, String targetUserLogin, int amount) {
        Debt debtFrom = debtService.findByUsers(currentUserLogin, targetUserLogin);
        Debt debtTo = debtService.findByUsers(targetUserLogin, currentUserLogin);
        int currentUserBalance = userService.findByLogin(currentUserLogin).getBalance();

        int transferredAmount = 0;
        if (debtFrom != null) { // Current user debtor
            debtService.increaseDebt(currentUserLogin, targetUserLogin, amount);
        } else if (debtTo != null) {  // Target user debtor
            if (debtTo.getAmount() <= amount) {
                debtService.decreaseDebt(targetUserLogin, currentUserLogin, amount);
                userService.decreaseBalance(currentUserLogin, amount - debtTo.getAmount());
                userService.increaseBalance(targetUserLogin, amount - debtTo.getAmount());
                transferredAmount = amount - debtTo.getAmount();
            } else {
                debtService.decreaseDebt(targetUserLogin, currentUserLogin, amount);
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

        return transferredAmount;
    }

    @Transactional
    public TransferDto deposit(String currentUserLogin, int amount) {
        Debt debt = debtService.findByDebtor(currentUserLogin);
        TransferDto transferDto = null;

        int remainingAmount = amount;
        if (debt != null) {
            debtService.decreaseDebt(currentUserLogin, debt.getBorrower(), amount);
            int decreasedAmount = Math.min(amount, debt.getAmount());
            userService.findByLogin(debt.getBorrower()).increaseBalance(decreasedAmount);
            transferDto = new TransferDto(decreasedAmount, debt.getBorrower())
            ;
            remainingAmount = amount - decreasedAmount;
        }
        if (remainingAmount > 0) {
            userService.increaseBalance(currentUserLogin, remainingAmount);
        }
        return transferDto;
    }

    @Transactional
    public int withdraw(String currentUserLogin, int amount) {
        if (userService.findByLogin(currentUserLogin).getBalance() < amount)
            return 0;
        userService.decreaseBalance(currentUserLogin, amount);
        return amount;
    }
}

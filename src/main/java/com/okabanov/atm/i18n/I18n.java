package com.okabanov.atm.i18n;

import com.okabanov.dto.BalanceDto;
import com.okabanov.dto.DebtDto;
import com.okabanov.dto.TransferDto;

public final class I18n {
    public static String incorrectArgumentCountMessage(int expected, int found) {
        return String.format("Incorrect arguments count (expected: %d, found: %d)", expected, found);
    }

    public static String balanceMessage(BalanceDto balanceDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Your balance is $%d\n", balanceDto.getBalance()));
        DebtDto owedTo = balanceDto.getOwedTo();
        DebtDto borrower = balanceDto.getOwedFrom();
        if (owedTo != null)
            stringBuilder.append(String.format("Owed $%d to %s\n", owedTo.getAmount(), owedTo.getUser()));
        if (borrower != null)
            stringBuilder.append(String.format("Owed $%d from %s\n", borrower.getAmount(), borrower.getUser()));
        return stringBuilder.toString();
    }

    public static String withdrawnMessage(int withdrawnAmount) {
        return String.format("Withdrawn $%d", withdrawnAmount);
    }

    public static String transferMessage(TransferDto transferDto) {
        return String.format("Transferred $%d to %s\n",
                transferDto.getTransferredAmount(),
                transferDto.getTransferTo()
        );
    }

    public static String goodbyeMessage(String userLogin) {
        return String.format("Goodbye, %s!\n", userLogin);
    }

    public static String notEnoughMoneyMessage() {
        return "Not enough money";
    }

    public static String useNotAuthorizedMessage() {
        return "User not authorized";
    }

    public static String unsupportedCommandMessage() {
        return "Unsupported command";
    }

    public static String welcomeMessage(String login) {
        return String.format("Hello, %s!", login);
    }

    public static String transferMessage(String targetUserLogin, int transferredAmount) {
        return String.format("Transferred $%d to %s", transferredAmount, targetUserLogin);
    }
}

package com.okabanov.atm.shell;

import com.okabanov.atm.exception.NotEnoughMoneyException;
import com.okabanov.atm.i18n.I18n;
import com.okabanov.server.ServerRPC;

import java.util.HashMap;

public class WithdrawMethod extends ShellMethod {
    private final ServerRPC serverRPC;

    public WithdrawMethod(HashMap<String, String> userState, ServerRPC serverRPC) {
        super(userState);
        this.serverRPC = serverRPC;
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
        String currentUser = getCurrentUser();

        int withdrawnAmount = serverRPC.withdraw(currentUser, amount);
        if (withdrawnAmount != amount && withdrawnAmount == 0)
            throw new NotEnoughMoneyException();

        return I18n.withdrawnMessage(withdrawnAmount) + "\n" + I18n.balanceMessage(serverRPC.balance(currentUser));
    }
}

package com.okabanov.atm.shell;

import com.okabanov.atm.exception.NotEnoughMoney;
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
        if (serverRPC.balance(currentUser).getBalance() < amount) // there is two step check;
            throw new NotEnoughMoney();

        int withdrawnAmount = serverRPC.withdraw(currentUser, amount);
        return String.format("Withdrawn $%d\n%s", withdrawnAmount, buildBalanceMessage(serverRPC.balance(currentUser)));
    }
}

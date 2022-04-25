package com.okabanov.atm.shell;

import com.okabanov.atm.exception.UnauthorizedException;
import com.okabanov.server.ServerRPC;

import java.util.HashMap;

public class TransferMethod extends ShellMethod {
    private final ServerRPC serverRPC;

    public TransferMethod(HashMap<String, String> userState, ServerRPC serverRPC) {
        super(userState);
        this.serverRPC = serverRPC;
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

        int transferredAmount = serverRPC.transfer(currentUserLogin, targetUserLogin, amount);
        String transferredMessage = "";
        if (transferredAmount > 0) {
            transferredMessage += String.format("Transferred $%d to %s\n", transferredAmount, targetUserLogin);
        }
        return transferredMessage + buildBalanceMessage(serverRPC.balance(currentUserLogin));
    }
}

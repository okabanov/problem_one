package com.okabanov.atm.shell;

import com.okabanov.atm.exception.UnauthorizedException;
import com.okabanov.dto.TransferDto;
import com.okabanov.server.ServerRPC;

import java.util.HashMap;

public class DepositMethod extends ShellMethod {
    private final ServerRPC serverRPC;

    public DepositMethod(HashMap<String, String> userState, ServerRPC serverRPC) {
        super(userState);
        this.serverRPC = serverRPC;
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

        TransferDto transferDto = serverRPC.deposit(currentUserLogin, amount);
        String transferMessage = "";
        if (transferDto != null) {
            transferMessage = String.format("Transferred $%d to %s\n",
                    transferDto.getTransferredAmount(),
                    transferDto.getTransferTo()
            );
        }
        return transferMessage + buildBalanceMessage(serverRPC.balance(currentUserLogin));
    }
}

package com.okabanov.atm.dispatcher;

import com.okabanov.atm.exception.IncorrectArgumentsCountException;
import com.okabanov.atm.exception.UnsupportedCommandException;
import com.okabanov.atm.shell.*;
import com.okabanov.server.ServerRPC;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RequestDispatcher {
    private final HashMap<String, String> userState = new HashMap<>();
    private final List<ShellMethod> availableShellMethods;

    public RequestDispatcher(ServerRPC serverRPC) {
        // We can collect this list using reflection and DI
        availableShellMethods = Arrays.asList(
                new LoginMethod(userState, serverRPC),
                new LogoutMethod(userState),
                new DepositMethod(userState, serverRPC),
                new TransferMethod(userState, serverRPC),
                new WithdrawMethod(userState, serverRPC)
        );
    }

    public String dispatchRequest(String inputLine) {
        try {
            if (inputLine == null || "exit".equals(inputLine)) {
                System.out.println("\nGoodbye!");
                System.exit(0);
            }
            if (inputLine.isBlank())
                return "";

            String[] commandParts = inputLine.trim().split(" ");

            ShellMethod shellMethod = availableShellMethods.stream()
                    .filter(sm -> sm.getMethodName().equals(commandParts[0]))
                    .findFirst()
                    .orElseThrow(UnsupportedCommandException::new);
            if (commandParts.length - 1 != shellMethod.getCountArguments())
                throw new IncorrectArgumentsCountException(shellMethod.getCountArguments(), commandParts.length - 1);

            return shellMethod.executeMethod(commandParts[0], Arrays.copyOfRange(commandParts, 1, commandParts.length));
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}

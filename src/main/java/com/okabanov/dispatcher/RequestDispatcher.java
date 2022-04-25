package com.okabanov.dispatcher;

import com.okabanov.exception.IncorrectArgumentsCountException;
import com.okabanov.exception.UnsupportedCommandException;
import com.okabanov.service.BalanceService;
import com.okabanov.service.DebtService;
import com.okabanov.service.UserService;
import com.okabanov.shell.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RequestDispatcher {
    private HashMap<String, String> userState = new HashMap<>();
    private List<ShellMethod> availableShellMethods;

    public RequestDispatcher(UserService userService, DebtService debtService, BalanceService balanceService) {
        // We can collect this list using reflection and DI
        availableShellMethods = Arrays.asList(
                new LoginMethod(userState, userService, balanceService),
                new LogoutMethod(userState),
                new DepositMethod(userState, userService, debtService, balanceService),
                new TransferMethod(userState, userService, debtService, balanceService),
                new WithdrawMethod(userState, userService, balanceService)
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
                    .orElse(null);
            if (shellMethod == null)
                throw new UnsupportedCommandException();
            if (commandParts.length - 1 != shellMethod.getCountArguments())
                throw new IncorrectArgumentsCountException(shellMethod.getCountArguments(), commandParts.length - 1);

            return shellMethod.executeMethod(commandParts[0], Arrays.copyOfRange(commandParts, 1, commandParts.length));
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}

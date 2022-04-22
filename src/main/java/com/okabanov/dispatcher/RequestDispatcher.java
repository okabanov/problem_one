package com.okabanov.dispatcher;

import com.okabanov.exception.IncorrectArgumentsCountException;
import com.okabanov.exception.ShellMethodNotFound;
import com.okabanov.exception.UnsupportedCommandException;
import com.okabanov.service.BalanceService;
import com.okabanov.service.DebtService;
import com.okabanov.service.UserService;
import com.okabanov.shell.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RequestDispatcher {
    private HashMap<String, Object> userState = new HashMap<>();
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

    public String dispatchRequest(String str) {
        try {
            if (str == null || "exit".equals(str)) {
                System.out.println("\nGoodbye!");
                System.exit(0);
            }
            String[] s = str.trim().split(" ");
            if (s.length == 1 && s[0].equals(""))
                throw new ShellMethodNotFound();

            ShellMethod shellMethod = availableShellMethods.stream()
                    .filter(sm -> sm.getMethodName().equals(s[0]))
                    .findAny()
                    .orElse(null);
            if (shellMethod == null)
                throw new UnsupportedCommandException();
            if (s.length - 1 != shellMethod.getCountArguments())
                throw new IncorrectArgumentsCountException(shellMethod.getCountArguments(), s.length - 1);

            return shellMethod.executeMethod(s[0], Arrays.copyOfRange(s, 1, s.length));
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}

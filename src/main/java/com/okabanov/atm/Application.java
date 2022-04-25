package com.okabanov.atm;

import com.okabanov.atm.dispatcher.RequestDispatcher;
import com.okabanov.server.ServerRPC;
import com.okabanov.server.service.BalanceService;
import com.okabanov.server.service.DebtService;
import com.okabanov.server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
    // DI instead of it
    private final UserService userService = new UserService();
    private final DebtService debtService = new DebtService();

    // It is creations should be in Server App

    private final ServerRPC serverRPC = new ServerRPC(
            userService,
            debtService,
            new BalanceService(debtService, userService)
    );

    private final RequestDispatcher requestDispatcher = new RequestDispatcher(serverRPC);

    private final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        new Application().run();
    }

    public void run() throws IOException {
        while (true) {
            String request = readConsoleCommand("$ ");
            String result = requestDispatcher.dispatchRequest(request);
            System.out.println(result);
        }
    }

    private String readConsoleCommand(String msg) throws IOException {
        System.out.print(msg);
        return consoleReader.readLine();
    }

}
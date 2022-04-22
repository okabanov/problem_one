package com.okabanov;

import com.okabanov.dispatcher.RequestDispatcher;
import com.okabanov.service.BalanceService;
import com.okabanov.service.DebtService;
import com.okabanov.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
    private UserService userService = new UserService();
    private DebtService debtService = new DebtService();

    // DI instead of Spring beans
    private RequestDispatcher requestDispatcher = new RequestDispatcher(
            userService,
            debtService,
            new BalanceService(debtService, userService)
    );

    private BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

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
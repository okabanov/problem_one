package com.okabanov;

import com.okabanov.parser.Command;
import com.okabanov.parser.CommandParser;
import com.okabanov.controller.BankController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {

    CommandParser parser = new CommandParser();

    BankController bankService = new BankController();

    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        new Application().run();
    }

    public void run() throws IOException {
        while (true) {
            String request = readConsoleCommand("$ ");
            dispatchRequest(request);
        }
    }

    private void dispatchRequest(String str) {
        try {
            Command command = parser.parse(str);
            switch (command.getAction()) {
                case LOGIN:
                    bankService.login(command.getArguments()[0]);
                    break;
                case DEPOSIT:
                    bankService.deposit(command.getArguments()[0]);
                    break;
                case WITHDRAW:
                    break;
                case TRANSFER:
                    break;
                case LOGOUT:
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private String readConsoleCommand(String format, Object... args) throws IOException {
        System.out.print(String.format(format, args));
        return consoleReader.readLine();
    }

}
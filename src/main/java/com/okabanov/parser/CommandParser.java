package com.okabanov.parser;

import java.util.Arrays;

public class CommandParser {
    public Command parse(String consoleLine) {
        String[] s = consoleLine.trim().split(" ");
        if (s.length == 1 && s[0].equals(""))
            throw new IllegalArgumentException("Use one of available commands");

        Action action;
        try {
            action = Action.valueOf(s[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported command");
        }

        if (s.length - 1 != action.getCountArguments())
            throw new IllegalArgumentException("Incorrect arguments count");

        return new Command(action, Arrays.copyOfRange(s, 1, s.length));
    }
}

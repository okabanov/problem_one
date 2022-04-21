package com.okabanov.parser;

public class Command {
    private Action action;
    private String[] arguments;

    public Command(Action action, String[] arguments) {
        this.action = action;
        this.arguments = arguments;
    }

    public Action getAction() {
        return action;
    }

    public String[] getArguments() {
        return arguments;
    }
}

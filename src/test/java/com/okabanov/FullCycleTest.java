package com.okabanov;

import org.junit.jupiter.api.Test;

public class FullCycleTest extends BaseShellTest {
    @Test
    void checkDemoShellCommands() {
        testDispatchCommand("login Alice", "Hello, Alice!\nYour balance is $0\n");
        testDispatchCommand("deposit 100", "Your balance is $100\n");
        testDispatchCommand("logout", "Goodbye, Alice!\n");

        testDispatchCommand("login Bob", "Hello, Bob!\nYour balance is $0\n");
        testDispatchCommand("deposit 80", "Your balance is $80\n");
        testDispatchCommand("transfer Alice 50", "Transferred $50 to Alice\nYour balance is $30\n");
        testDispatchCommand("transfer Alice 100", "Transferred $30 to Alice\nYour balance is $0\nOwed $70 to Alice\n");
        testDispatchCommand("deposit 30", "Transferred $30 to Alice\nYour balance is $0\nOwed $40 to Alice\n");
        testDispatchCommand("logout", "Goodbye, Bob!\n");

        testDispatchCommand("login Alice", "Hello, Alice!\nYour balance is $210\nOwed $40 from Bob\n");
        testDispatchCommand("transfer Bob 30", "Your balance is $210\nOwed $10 from Bob\n");
        testDispatchCommand("logout", "Goodbye, Alice!\n");

        testDispatchCommand("login Bob", "Hello, Bob!\nYour balance is $0\nOwed $10 to Alice\n");
        testDispatchCommand("deposit 100", "Transferred $10 to Alice\nYour balance is $90\n");
        testDispatchCommand("logout", "Goodbye, Bob!\n");
    }
}

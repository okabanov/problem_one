package com.okabanov;

import org.junit.jupiter.api.Test;

public class ShellMethodsTest extends BaseShellTest {
    @Test
    void loginLogoutMethodTest() {
        testDispatchCommand("login user_1", "Hello, user_1!\nYour balance is $0\n");
        testDispatchCommand("logout", "Goodbye, user_1!\n");

        testDispatchCommand("login user_2", "Hello, user_2!\nYour balance is $0\n");
        testDispatchCommand("logout", "Goodbye, user_2!\n");

        testDispatchCommand("logout", "User not authorized");
    }

    @Test
    void depositMethodTest() {
        testDispatchCommand("login user_1", "Hello, user_1!\nYour balance is $0\n");
        testDispatchCommand("deposit 10", "Your balance is $10\n");
        testDispatchCommand("deposit 10", "Your balance is $20\n");

        runDispatchCommand("logout");
        runDispatchCommand("login user_2");
        runDispatchCommand("logout");
        runDispatchCommand("login user_1");
        runDispatchCommand("transfer user_2 30");

        testDispatchCommand("deposit 5", "Transferred $5 to user_2\nYour balance is $0\nOwed $5 to user_2\n");
        testDispatchCommand("deposit 5", "Transferred $5 to user_2\nYour balance is $0\n");
        testDispatchCommand("deposit 5", "Your balance is $5\n");
    }

    @Test
    void transferMethodTest() {
        runDispatchCommand("login user_1");
        runDispatchCommand("deposit 10");

        runDispatchCommand("logout");
        runDispatchCommand("login user_2");
        runDispatchCommand("logout");
        runDispatchCommand("login user_1");
        testDispatchCommand("transfer user_2 30", "Transferred $10 to user_2\nYour balance is $0\nOwed $20 to user_2\n");
        testDispatchCommand("transfer user_2 10", "Your balance is $0\nOwed $30 to user_2\n");

        runDispatchCommand("logout");
        runDispatchCommand("login user_2");
        // ??
        testDispatchCommand("transfer user_1 10", "Your balance is $10\nOwed $20 from user_1\n");
        testDispatchCommand("transfer user_1 10", "Your balance is $10\nOwed $10 from user_1\n");
        testDispatchCommand("transfer user_1 10", "Your balance is $10\n");
        testDispatchCommand("transfer user_1 10", "Transferred $10 to user_1\nYour balance is $0\n");
        testDispatchCommand("transfer user_1 10", "Your balance is $0\nOwed $10 to user_1\n");
    }

    @Test
    void withdrawMethodTest() {
        testDispatchCommand("login Alice", "Hello, Alice!\nYour balance is $0\n");
        testDispatchCommand("deposit 100", "Your balance is $100\n");
        testDispatchCommand("withdraw 60", "Withdrawn $60\nYour balance is $40\n");
        testDispatchCommand("withdraw 50", "Not enough money");
    }
}

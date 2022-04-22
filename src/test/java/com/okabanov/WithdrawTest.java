package com.okabanov;

import com.okabanov.dispatcher.RequestDispatcher;
import com.okabanov.service.BalanceService;
import com.okabanov.service.DebtService;
import com.okabanov.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WithdrawTest {

    private UserService userService = new UserService();
    private DebtService debtService = new DebtService();

    RequestDispatcher requestDispatcher = new RequestDispatcher(
            userService,
            debtService,
            new BalanceService(debtService, userService)
    );

    @Test
    void checkDemoShellCommands() {
        testDispatchCommand("login Alice", "Hello, Alice!\nYour balance is $0\n");
        testDispatchCommand("deposit 100", "Your balance is $100\n");
        testDispatchCommand("withdraw 60", "Withdrawn $60\nYour balance is $40\n");
        testDispatchCommand("withdraw 50", "Not enough money");
    }

    private void testDispatchCommand(String command, String expected) {
        String actual = requestDispatcher.dispatchRequest(command);
        assertEquals(expected, actual);
    }
}

package com.okabanov;

import com.okabanov.dispatcher.RequestDispatcher;
import com.okabanov.service.BalanceService;
import com.okabanov.service.DebtService;
import com.okabanov.service.UserService;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseShellTest {
    protected RequestDispatcher requestDispatcher = initDispatcher();

    protected void testDispatchCommand(String command, String expected) {
        String actual = requestDispatcher.dispatchRequest(command);
        assertEquals(expected, actual);
    }

    protected void runDispatchCommand(String command) {
        requestDispatcher.dispatchRequest(command);
    }

    @BeforeEach
    protected void refreshDispatcherState() {
        requestDispatcher = initDispatcher();
    }

    private RequestDispatcher initDispatcher() {
        UserService userService = new UserService();
        DebtService debtService = new DebtService();
        return new RequestDispatcher(
                userService,
                debtService,
                new BalanceService(debtService, userService)
        );
    }
}

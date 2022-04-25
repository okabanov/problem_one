package com.okabanov;

import com.okabanov.atm.dispatcher.RequestDispatcher;
import com.okabanov.server.ServerRPC;
import com.okabanov.server.service.BalanceService;
import com.okabanov.server.service.DebtService;
import com.okabanov.server.service.UserService;
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
        // DI instead of it
        UserService userService = new UserService();
        DebtService debtService = new DebtService();

        // It is creations should be in Server App

        ServerRPC serverRPC = new ServerRPC(
                userService,
                debtService,
                new BalanceService(debtService, userService)
        );

        return new RequestDispatcher(serverRPC);
    }
}

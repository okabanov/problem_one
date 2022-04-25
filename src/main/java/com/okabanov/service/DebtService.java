package com.okabanov.service;

import com.okabanov.model.Debt;

import java.util.ArrayList;
import java.util.Iterator;

public class DebtService {
    private final ArrayList<Debt> debtState = new ArrayList<Debt>();

    public Debt findByUsers(String debtor, String borrower) {
        return debtState.stream()
                .filter(debt -> debtor.equals(debt.getDebtor()) && borrower.equals(borrower))
                .findAny()
                .orElse(null);
    }

    public Debt findByDebtor(String debtor) {
        return debtState.stream()
                .filter(debt -> debtor.equals(debt.getDebtor()))
                .findAny()
                .orElse(null);
    }

    public Debt findByBorrower(String borrower) {
        return debtState.stream()
                .filter(debt -> borrower.equals(debt.getBorrower()))
                .findAny()
                .orElse(null);
    }

    public void deleteDebt(String debtor, String borrower) {
        Iterator<Debt> itr = debtState.iterator();
        while (itr.hasNext()) {
            Debt next = itr.next();
            if (next.getDebtor().equals(debtor) && next.getBorrower().equals(borrower))
                itr.remove();
        }
    }

    public void createDebt(String debtor, String borrower, int amount) {
        debtState.add(new Debt(debtor, borrower, amount));
    }

    public void decreaseDebt(String debtor, String borrower, int amount) {
        Debt debt = findByUsers(debtor, borrower);
        if (debt.getAmount() <= amount) {
            deleteDebt(debtor, borrower);
        } else {
            debt.decreaseAmount(amount);
        }
    }

    public void increaseDebt(String debtor, String borrower, int amount) {
        Debt debt = findByUsers(debtor, borrower);
        if (debt == null) {
            createDebt(debtor, borrower, amount);
        } else {
            debt.increaseAmount(amount);
        }
    }
}

package com.okabanov.service;

import com.okabanov.model.Debt;

import java.util.ArrayList;
import java.util.Iterator;

public class DebtService {
    private ArrayList<Debt> debtState = new ArrayList<Debt>();

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

    public void deleteDebtor(String debtor) {
        Iterator<Debt> itr = debtState.iterator();
        while (itr.hasNext()) {
            if (itr.next().getDebtor().equals(debtor))
                itr.remove();
        }
    }

    public void createDebt(String debtor, String borrower, int amount) {
        debtState.add(new Debt(debtor, borrower, amount));
    }
}

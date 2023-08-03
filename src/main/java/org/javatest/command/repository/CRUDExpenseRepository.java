package org.javatest.command.repository;

public class CRUDExpenseRepository<K extends Number, Expense> implements Repository<Expense> {

    @Override
    public void save(Expense object) {
        // TODO
    }

    public Expense getById(K id) {
        // TODO
        return null;
    }

    public Expense getByMessage(String message) {
        // TODO
        return null;
    }

    public Expense getByQuota(double quota) {
        // TODO
        return null;
    }

}

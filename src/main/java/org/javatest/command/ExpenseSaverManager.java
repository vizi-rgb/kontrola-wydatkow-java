package org.javatest.command;

import org.javatest.command.ExpenseSaver;

public class ExpenseSaverManager {
    private static ExpenseSaver expenseSaver;

    public static <T extends Record> ExpenseSaver<T> getExpenseSaver() {
        return expenseSaver;
    }

    public static <T extends Record> void  setExpenseSaver(ExpenseSaver<T> expenseSaver1) {
        if (expenseSaver != null) {
            throw new IllegalArgumentException("Cannot override previously assigned expense saver");
        }

        expenseSaver = expenseSaver1;
    }
}

package org.javatest.command;

public interface ExpenseSaver<T extends Record> {

    void save(T expense);
}

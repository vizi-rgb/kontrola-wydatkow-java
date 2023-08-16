package org.javatest.command.repository;

import org.javatest.command.MyDateFormat;

public record Expense(Long id, double money, String message, MyDateFormat date) {
    public Expense(double money, String message, MyDateFormat date) {
        this(null, money, message, date);
    }
}

package org.javatest.command.repository;


public record Expense(Long id, double money, String message, String date) {
    public Expense(double money, String message, String date) {
        this(null, money, message, date);
    }
}

package org.javatest.handlers;

import org.javatest.command.CriticalError;
import org.javatest.command.Handler;
import org.javatest.command.MyDateFormat;
import org.javatest.command.repository.Expense;
import org.javatest.command.repository.Repository;
import org.javatest.command.repository.RepositoryManager;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddHandler implements Handler {

    private RepositoryManager<? extends Repository<Expense>> repositoryManager;
    private String message;
    private MyDateFormat date;
    private Double money;

    public <T extends Repository<Expense>> AddHandler(RepositoryManager<T> repositoryManager) {
        this.repositoryManager = repositoryManager;
        this.message = "";
        this.date = currentDate();
    }


    @Override
    public void handle(String[] options) {
        for (int i = 0; i < options.length; i++) {
            switch (options[i]) {
                case "-m":
                    try {
                        this.message = options[++i];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        CriticalError.report("Missing message after -m");
                    }

                    break;

                case "-d":
                    String[] dateString = null;

                    try {
                        dateString = parseDate(options[++i]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        CriticalError.report("Missing date after -d");
                    }

                    try {
                        assignDate(dateString);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        CriticalError.report("Wrong date format\nExpecting: [dd].[mm].[yyyy]");
                    }

                    break;

                default:
                    Pattern pattern = Pattern.compile("\\d+[.|,]?\\d*");
                    Matcher matcher = pattern.matcher(options[i]);
                    if (matcher.find()) {
                        String moneyDotSeparated = options[i].replace(",", ".");
                        this.money = Double.parseDouble(moneyDotSeparated);
                    } else {
                        CriticalError.report("Option not found");
                    }
            }
        }

        if (money == null) {
            CriticalError.report("Money parameter is required");
        }

        final Expense expense = new Expense(money, message, date);
        try {
            repositoryManager.getRepository().save(expense);
        } catch (NullPointerException e) {
            CriticalError.report("Repository passed by RepositoryManager is null");
        }
    }

    private MyDateFormat currentDate() {
        String[] date = LocalDate.now().toString().split("-");
        int day = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[0]);

        return new MyDateFormat(day, month, year);
    }

    private String[] parseDate(String input) {
        String[] parsedDate = input.split("[.]");
        return parsedDate;
    }

    private void assignDate(String[] dateString) {
        switch (dateString.length) {
            case 1: {
                int day = Integer.parseInt(dateString[0]);
                LocalDate date = LocalDate.now();
                this.date = new MyDateFormat(day, date.getMonthValue(), date.getYear());
                break;
            }

            case 2: {
                int day = Integer.parseInt(dateString[0]);
                int month = Integer.parseInt(dateString[1]);
                LocalDate date = LocalDate.now();
                this.date = new MyDateFormat(day, month, date.getYear());
                break;
            }

            case 3: {
                int day = Integer.parseInt(dateString[0]);
                int month = Integer.parseInt(dateString[1]);
                int year = Integer.parseInt(dateString[2]);
                this.date = new MyDateFormat(day, month, year);
                break;
            }

            default:
                throw new ArrayIndexOutOfBoundsException("Length " + dateString.length);
        }
    }
}

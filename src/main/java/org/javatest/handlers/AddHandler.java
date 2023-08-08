package org.javatest.handlers;

import org.javatest.command.CriticalError;
import org.javatest.command.Handler;
import org.javatest.command.repository.Expense;
import org.javatest.command.repository.Repository;
import org.javatest.command.repository.RepositoryManager;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddHandler implements Handler {

    private RepositoryManager<? extends Repository<Expense>> repositoryManager;
    private String message;
    private String date;
    private Double money;

    public <T extends Repository<Expense>> AddHandler(RepositoryManager<T> repositoryManager) {
        this.repositoryManager = repositoryManager;
        this.message = "";
        this.date = currentDateInDotFormat();
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

    private String currentDateInDotFormat() {
        String[] date = LocalDate.now().toString().split("-");
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = date.length - 1; i >= 0; i--) {
            stringBuilder.append(date[i]).append(".");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private String[] parseDate(String input) {
        String[] parsedDate = input.split("[.]");
        return parsedDate;
    }

    private void assignDate(String[] dateString) {
        switch (dateString.length) {
            case 1: {
                String day = getDay(dateString[0]);
                LocalDate date = LocalDate.now();
                this.date = day + "." + date.getMonthValue() + "." + date.getYear();
                break;
            }

            case 2: {
                String day = getDay(dateString[0]);
                String month = getMonth(dateString[1]);
                this.date = day + "." + month + "." + LocalDate.now().getYear();
                break;
            }

            case 3: {
                String day = getDay(dateString[0]);
                String month = getMonth(dateString[1]);
                String year = dateString[2];
                this.date = day + "." + month + "." + year;
                break;
            }

            default:
                throw new ArrayIndexOutOfBoundsException("Length " + dateString.length);
        }
    }

    private String getMonth(String month) {
        return month.length() == 1 ? "0" + month : month;
    }

    private String getDay(String day) {
        return day.length() == 1 ? "0" + day : day;
    }
}

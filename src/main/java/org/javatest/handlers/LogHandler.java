package org.javatest.handlers;

import org.javatest.command.CriticalError;
import org.javatest.command.Handler;
import org.javatest.command.repository.CRUDExpenseRepository;
import org.javatest.command.repository.Expense;
import org.javatest.command.repository.RepositoryManager;

import java.util.*;

public class LogHandler implements Handler {

    private RepositoryManager<CRUDExpenseRepository> repositoryManager;

    private enum FLAGS {
        MONTHLY("m"),
        YEARLY("y"),
        ALLTIME("a");

        private final String actuator;

        private FLAGS(String actuator) {
            this.actuator = actuator;
        }

        public String getActuator() {
            return actuator;
        }
    }

    private final EnumSet<FLAGS> flags;

    public LogHandler(RepositoryManager<CRUDExpenseRepository> repositoryManager) {
        this.repositoryManager = repositoryManager;
        flags = EnumSet.noneOf(FLAGS.class);
    }

    @Override
    public void handle(String[] options) {
        for (int i = 0; i < options.length; i++) {
            getRequestedFlags(options[i]);
        }

        Collection<Expense> expensesCollection = repositoryManager.getRepository().getAll();
        ArrayList<Expense> expenses = new ArrayList<>(expensesCollection);

        for (FLAGS flag : flags) {
            switch (flag) {
                case YEARLY: {
                    String toPrint = prepareYearlyReport(expenses);
                    System.out.println(toPrint);
                    break;
                }
                case ALLTIME: {
                    String toPrint = prepareAlltimeReport(expenses);
                    System.out.println(toPrint);
                    break;
                }
                case MONTHLY: {
                    String toPrint = prepareMonthlyReport(expenses);
                    System.out.println(toPrint);
                    break;
                }
            }
        }

        if (flags.isEmpty()) {
            String toPrint = prepareDefaultReport(expenses);
            System.out.println(toPrint);
        }
    }

    private void getRequestedFlags(String option) {
        if (option.startsWith("-")) {
            for (int j = 1; j < option.length(); j++) {
                boolean matched = false;
                for (FLAGS flag : FLAGS.values()) {
                    if (option.charAt(j) == flag.getActuator().charAt(0)) {
                        flags.add(flag);
                        matched = true;
                        break;
                    }
                }

                if (!matched) {
                    CriticalError.report("Syntax error: -" + option.charAt(j) + "not recognized");
                }
            }
        }
    }

    private String prepareYearlyReport(ArrayList<Expense> expenses) {
        Comparator<Expense> byYear = Comparator.comparingInt(a -> a.date().year());
        Collections.sort(expenses, byYear);

        StringBuilder stringBuilder = new StringBuilder();

        int year = expenses.get(0).date().year();
        double sum = expenses.get(0).money();
        stringBuilder.append("-- " + year + " --\n");
        for (int i = 1; i < expenses.size(); i++) {
            Expense currentExpense = expenses.get(i);

            if (year != currentExpense.date().year()) {
                stringBuilder.append("Sum: " + sum + "\n\n");

                year = currentExpense.date().year();
                sum = currentExpense.money();

                stringBuilder.append("-- " + year + " --\n");
            } else {
                sum += currentExpense.money();
            }
        }

        stringBuilder.append("Sum: " + sum);

        return stringBuilder.toString();
    }

    private String prepareAlltimeReport(ArrayList<Expense> expenses) {
        StringBuilder stringBuilder = new StringBuilder();

        double sum = 0;
        for (Expense e : expenses) {
            sum += e.money();
        }

        stringBuilder.append("ALLTIME: " + sum + "\n");
        return stringBuilder.toString();
    }

    private String prepareMonthlyReport(ArrayList<Expense> expenses) {
        Comparator<Expense> byYear = Comparator.comparingInt(a -> a.date().year());
        Comparator<Expense> byMonth = Comparator.comparingInt(a -> a.date().month());
        Collections.sort(expenses, byMonth);
        Collections.sort(expenses, byYear);

        StringBuilder stringBuilder = new StringBuilder();

        int year = expenses.get(0).date().year();
        int month = expenses.get(0).date().month();
        double sum = expenses.get(0).money();
        stringBuilder.append("-- " + (month < 10 ? "0" + month : month) + "." + year +  " --\n");
        for (int i = 1; i < expenses.size(); i++) {
            Expense currentExpense = expenses.get(i);

            if (month != currentExpense.date().month() || year != currentExpense.date().year()) {
                stringBuilder.append("Sum: " + sum + "\n\n");

                year = currentExpense.date().year();
                month = currentExpense.date().month();
                sum = currentExpense.money();

                stringBuilder.append("-- " + (month < 10 ? "0" + month : month) + "." + year + " --\n");
            } else {
                sum += currentExpense.money();
            }
        }

        stringBuilder.append("Sum: " + sum);

        return stringBuilder.toString();
    }

    private String prepareDefaultReport(ArrayList<Expense> expenses) {
        Comparator<Expense> byYear = Comparator.comparingInt(a -> a.date().year());
        Comparator<Expense> byMonth = Comparator.comparingInt(a -> a.date().month());
        Comparator<Expense> byDay = Comparator.comparingInt(a -> a.date().day());

        Collections.sort(expenses, byDay);
        Collections.sort(expenses, byMonth);
        Collections.sort(expenses, byYear);

        StringBuilder stringBuilder = new StringBuilder();

        for (Expense e : expenses) {
            stringBuilder.append("ID: "  + e.id() + "\n");
            stringBuilder.append("Money: " + e.money() + "\n");
            stringBuilder.append("Date: " + e.date().toString() + "\n");
            stringBuilder.append("Message: " + e.message() + "\n\n");
        }

        return stringBuilder.toString();
    }
}

package org.javatest.handlers;

import org.javatest.command.Handler;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddHandler implements Handler {

    private String message;
    private String date;
    private double money;

    public AddHandler() {
        this.message = "";
        // LocalDate.now().toString(): yyyy-mm-dd
        String[] date = LocalDate.now().toString().split("-");
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = date.length - 1; i >= 0; i--) {
            stringBuilder.append(date[i]).append(".");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        this.date = stringBuilder.toString();
    }

    @Override
    public void handle(String[] options) {
        for (int i = 0; i < options.length; i++) {
            switch (options[i]) {
                case "-m":
                    try {
                        this.message = options[++i];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Missing message after -m");
                        return;
                    }

                    break;

                case "-d":
                    String[] dateString;

                    try {
                        dateString = parseDate(options[++i]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Missing date after -d");
                        return;
                    }

                    try {
                        assignDate(dateString);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Wrong date format\nExpecting: [dd].[mm].[yyyy]");
                        return;
                    }

                    break;

                default:
                    Pattern pattern = Pattern.compile("\\d+[.|,]?\\d*");
                    Matcher matcher = pattern.matcher(options[i]);
                    if (matcher.find()) {
                        String moneyDotSeparated = options[i].replace(",", ".");
                        this.money = Double.parseDouble(moneyDotSeparated);
                    } else {
                        System.out.println("Option not found");
                        return;
                    }
            }
        }
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
                this.date = day + "." + date.getMonth() + "." + date.getYear();
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

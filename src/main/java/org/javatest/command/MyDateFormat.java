package org.javatest.command;

public record MyDateFormat(int day, int month, int year) {

    public MyDateFormat {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Day must be in <1, 31> range");
        }

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be in <1, 12> range");
        }
    }

    @Override
    public String toString() {
       return padIfNeeded(Integer.toString(day)) + "." +
               padIfNeeded(Integer.toString(month)) + "." +
               year;
    }

    private String padIfNeeded(String date) {
        if (date.length() < 2) {
            date = "0" + date;
        }

        return date;
    }
}

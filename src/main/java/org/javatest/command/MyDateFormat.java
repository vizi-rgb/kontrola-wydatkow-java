package org.javatest.command;

public record MyDateFormat(int day, int month, int year) {

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

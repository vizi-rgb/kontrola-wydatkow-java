package org.javatest.command;

public class CriticalError {
    public static void report(String message) {
        System.err.println(message);
        System.exit(-1);
    }
}

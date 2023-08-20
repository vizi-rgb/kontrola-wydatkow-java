package org.javatest.command;

import lombok.NonNull;

import java.util.Arrays;

public class CommandRunner {
    private CommandPool commandPool;

    public CommandRunner(@NonNull CommandPool commandPool) {
        this.commandPool = commandPool;
    }

    public void run(@NonNull String[] args) {
        if (args.length < 1) {
            printHelp("kontrola-wydatkow-java");
            return;
        }

        for (Command c : commandPool) {
            if (c.getActuator().equals(args[0])) {
                Handler handler = c.getHandler();
                String[] options = Arrays.copyOfRange(args, 1, args.length);
                handler.handle(options);
                return;
            }
        }

        System.err.println("Command not found");
    }

    private void printHelp(String programName) {
        System.err.println("Syntax:");
        for (Command c : commandPool) {
            System.err.println(programName + " " + c.getActuator());
            System.err.println(c.getDescription() + "\n");
        }
    }
}

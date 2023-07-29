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
            // TODO: print help
            return;
        }

        for (Command c : commandPool) {
            if (c.getActuator().equals(args[0])) {
                Handler handler = c.getHandler();
                String[] options = Arrays.copyOfRange(args, 1, args.length);
                handler.handle(options);
            } else {
                System.out.println("Command not found");
                return;
            }
        }
    }
}

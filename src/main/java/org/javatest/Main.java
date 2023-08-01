package org.javatest;

import org.javatest.command.*;
import org.javatest.handlers.AddHandler;

public class Main {
    public static void main(String[] args) {
        CommandBuilder builder = new CommandBuilder();
        CommandPool commands = new CommandPool();
        commands.add(
                builder
                        .withActuator("add")
                        .withDescription("Add new")
                        .withHandler(new AddHandler())
                        .build()
        );
        ExpenseSaverManager.setExpenseSaver(new DBExpenseSaver<Expense>());

        CommandRunner runner = new CommandRunner(commands);
        runner.run(args);
    }
}
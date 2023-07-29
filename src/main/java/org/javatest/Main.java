package org.javatest;

import org.javatest.command.Command;
import org.javatest.command.CommandBuilder;
import org.javatest.command.CommandPool;
import org.javatest.command.CommandRunner;
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

        CommandRunner runner = new CommandRunner(commands);
        runner.run(args);
    }
}
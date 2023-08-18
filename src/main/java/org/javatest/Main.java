package org.javatest;

import org.javatest.command.*;
import org.javatest.command.repository.CRUDExpenseRepository;
import org.javatest.command.repository.RepositoryManager;
import org.javatest.handlers.AddHandler;
import org.javatest.handlers.LogHandler;
import org.javatest.handlers.RemoveHandler;

public class Main {
    public static void main(String[] args) {
        RepositoryManager<CRUDExpenseRepository> repositoryManager = new RepositoryManager<>();
        repositoryManager.setRepository(new CRUDExpenseRepository());

        CommandBuilder builder = new CommandBuilder();
        CommandPool commands = new CommandPool();
        commands.add(
                builder
                        .withActuator("add")
                        .withDescription("Add new expense")
                        .withHandler(new AddHandler(repositoryManager))
                        .build(),

                builder
                        .withActuator("log")
                        .withDescription("Show saved expenses")
                        .withHandler(new LogHandler(repositoryManager))
                        .build(),
                builder
                        .withActuator("delete")
                        .withDescription("Delete expense with the provided ID")
                        .withHandler(new RemoveHandler(repositoryManager))
                        .build()

        );

        CommandRunner runner = new CommandRunner(commands);
        runner.run(args);
    }
}
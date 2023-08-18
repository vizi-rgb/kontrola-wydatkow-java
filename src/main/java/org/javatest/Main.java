package org.javatest;

import org.javatest.command.CommandBuilder;
import org.javatest.command.CommandPool;
import org.javatest.command.CommandRunner;
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
                        .withDescription("Add new expense\n" +
                                "Example: add -m \"example\" -d 1.1.1990 167.85\n" +
                                "-m Specify message\n" +
                                "-d Specify date (Date today if not specified)\n")
                        .withHandler(new AddHandler(repositoryManager))
                        .build(),

                builder
                        .withActuator("log")
                        .withDescription("Show saved expenses\n" +
                                "Example: log -m\n" +
                                "-m Monthly report\n" +
                                "-y Yearly report\n" +
                                "-a All-time report\n")
                        .withHandler(new LogHandler(repositoryManager))
                        .build(),
                builder
                        .withActuator("delete")
                        .withDescription("Delete expense with the provided ID\n" +
                                "Example: delete 76\n")
                        .withHandler(new RemoveHandler(repositoryManager))
                        .build()

        );

        CommandRunner runner = new CommandRunner(commands);
        runner.run(args);
    }
}
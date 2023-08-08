package org.javatest;

import org.javatest.command.*;
import org.javatest.command.repository.CRUDExpenseRepository;
import org.javatest.command.repository.RepositoryManager;
import org.javatest.handlers.AddHandler;

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
                        .build()
        );

        CommandRunner runner = new CommandRunner(commands);
        runner.run(args);
    }
}
package org.javatest.handlers;

import org.javatest.command.CriticalError;
import org.javatest.command.Handler;
import org.javatest.command.repository.CRUDExpenseRepository;
import org.javatest.command.repository.RepositoryManager;

public class RemoveHandler implements Handler {

    private RepositoryManager<CRUDExpenseRepository> repositoryManager;

    public RemoveHandler(RepositoryManager<CRUDExpenseRepository> repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    @Override
    public void handle(String[] options) {
        try {
            long id = Long.parseLong(options[0]);
            repositoryManager.getRepository().deleteById(id);
            System.out.println("Deleted expense with ID: " + id);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            CriticalError.report("Provide expense ID to delete");
        }
    }
}

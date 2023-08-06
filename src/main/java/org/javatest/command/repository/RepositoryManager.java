package org.javatest.command.repository;

import lombok.NonNull;

public class RepositoryManager<T extends Repository<?>> {

    private T repository;

    public T getRepository() {
        return repository;
    }

    public void setRepository(@NonNull T repository) {
        this.repository = repository;
    }
}
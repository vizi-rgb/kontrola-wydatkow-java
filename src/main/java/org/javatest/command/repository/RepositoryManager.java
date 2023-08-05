package org.javatest.command.repository;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class RepositoryManager<T extends Repository<?>> {

    private T repository;

    public void setRepository(@NonNull T repository) {
        this.repository = repository;
    }
}

package org.javatest.command.repository;

public interface Repository<T> {
    void save(T object);
}

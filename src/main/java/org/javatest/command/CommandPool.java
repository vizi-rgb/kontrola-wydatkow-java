package org.javatest.command;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandPool implements Iterable<Command> {
    private ArrayList<Command> commandPool = new ArrayList<>();


    public void add(@NonNull Command... commands) {
        commandPool.addAll(List.of(commands));
    }

    public int getPoolSize() {
        return commandPool.size();
    }

    @Override
    public Iterator<Command> iterator() {
        return commandPool.iterator();
    }
}

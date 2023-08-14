package org.javatest.handlers;

import org.javatest.command.Handler;
import org.javatest.command.repository.Expense;
import org.javatest.command.repository.Repository;
import org.javatest.command.repository.RepositoryManager;

import java.util.EnumSet;

public class LogHandler implements Handler  {

    private RepositoryManager<? extends Repository<Expense>> repositoryManager;

    private enum FLAGS {
        MONTHLY("m"),
        YEARLY("y"),
        ONELINE("1"),
        ALLTIME("a");

        private final String actuator;

        private FLAGS(String actuator) {
            this.actuator = actuator;
        }

        public String getActuator() {
            return actuator;
        }
    }

    private final EnumSet<FLAGS> flags;

    public <T extends Repository<Expense>> LogHandler(RepositoryManager<T> repositoryManager) {
        this.repositoryManager = repositoryManager;
        flags = EnumSet.noneOf(FLAGS.class);
    }

    @Override
    public void handle(String[] options) {
        for (int i = 0; i < options.length; i++) {
            if (options[i].startsWith("-")) {
                for (int j = 1; j < options[i].length(); j++) {
                    for (FLAGS f : FLAGS.values()) {
                        if (options[i].charAt(j) == f.getActuator().charAt(0)) {
                            flags.add(f);
                            break;
                        }
                    }
                }
            }
        }
    }
}

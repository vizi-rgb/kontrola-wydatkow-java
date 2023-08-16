package org.javatest.handlers;

import org.javatest.command.CriticalError;
import org.javatest.command.Handler;
import org.javatest.command.repository.CRUDExpenseRepository;
import org.javatest.command.repository.Expense;
import org.javatest.command.repository.Repository;
import org.javatest.command.repository.RepositoryManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

public class LogHandler implements Handler  {

    private RepositoryManager<CRUDExpenseRepository> repositoryManager;

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

    public LogHandler(RepositoryManager<CRUDExpenseRepository> repositoryManager) {
        this.repositoryManager = repositoryManager;
        flags = EnumSet.noneOf(FLAGS.class);
    }

    @Override
    public void handle(String[] options) {
        for (int i = 0; i < options.length; i++) {
            getRequestedFlags(options[i]);
        }

        Collection<Expense> expenses = repositoryManager.getRepository().getAll();

        for (FLAGS flag : flags) {
            switch (flag) {
                case YEARLY: {

                    break;
                }
            }
        }

    }

    private void getRequestedFlags(String option) {
        if (option.startsWith("-")) {
            for (int j = 1; j < option.length(); j++) {
                boolean matched = false;
                for (FLAGS flag : FLAGS.values()) {
                    if (option.charAt(j) == flag.getActuator().charAt(0)) {
                        flags.add(flag);
                        matched = true;
                        break;
                    }
                }

                if (!matched) {
                    CriticalError.report("Syntax error: -" + option.charAt(j) + "not recognized");
                }
            }
        }
    }
}

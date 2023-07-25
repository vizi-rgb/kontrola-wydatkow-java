package org.javatest.command;

public class Command {

    private final String actuator;
    private final String description;
    private final Handler handler;

    public Command(CommandBuilder builder) {
        this.actuator = builder.getActuator();
        this.description = builder.getDescription();
        this.handler = builder.getHandler();
    }
}

package org.javatest.command;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Command {

    private final String actuator;
    private final String description;
    private final Handler handler;

    public Command(@NonNull CommandBuilder builder) {
        this.actuator = builder.getActuator();
        this.description = builder.getDescription();
        this.handler = builder.getHandler();
    }
}

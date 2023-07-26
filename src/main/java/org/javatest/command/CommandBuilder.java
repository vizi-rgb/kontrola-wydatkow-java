package org.javatest.command;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class CommandBuilder {

    private String actuator;
    private String description;
    private Handler handler;

    public CommandBuilder withActuator(@NonNull String actuator) {
        if (actuator.isBlank()) {
            throw new IllegalArgumentException("Actuator cannot be a blank string");
        }

        this.actuator = actuator;
        return this;
    }

    public CommandBuilder withDescription(String description) {
        if (description.isBlank()) {
            description = "Description not provided";
        }

        this.description = description;
        return this;
    }

    public CommandBuilder withHandler(@NonNull Handler handler) {
        this.handler = handler;
        return this;
    }

    public Command build() {
        return new Command(this);
    }
}


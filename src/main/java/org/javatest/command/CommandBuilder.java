package org.javatest.command;

import lombok.Getter;

@Getter
public class CommandBuilder {

    private String actuator;
    private String description;
    private Handler handler;

    public CommandBuilder withActuator(String actuator) {
        this.actuator = actuator;
        return this;
    }

    public CommandBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CommandBuilder withHandler(Handler handler) {
        this.handler = handler;
        return this;
    }

    public Command build() {
        return new Command(this);
    }
}


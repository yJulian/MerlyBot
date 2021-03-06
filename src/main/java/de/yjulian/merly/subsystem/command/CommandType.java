package de.yjulian.merly.subsystem.command;

public enum CommandType {

    GUILD("Guild"),
    USER("PrivateChat"),
    GENERIC("Generic");

    private final String name;

    CommandType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean equals(CommandType type) {
        return (this == GENERIC) || (this == type);
    }

}

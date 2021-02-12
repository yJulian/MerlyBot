package de.yjulian.merly.subsystem.command;

import java.util.List;

public interface CommandManager {

    void addCommand(Command command);

    Command getCommand(String prefix);
    Command getCommand(String prefix, CommandType type);

    List<Command> getCommands();
    List<Command> getCommands(CommandType type);

}

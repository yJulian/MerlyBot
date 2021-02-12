package de.yjulian.merly.subsystem.commands;

import java.util.List;

public interface CommandManager {

    void addCommand(GuildCommand guildCommand);
    void addCommand(UserCommand command);

    Command getCommand(String prefix);
    Command getCommand(String prefix, CommandType type);

    List<Command> getCommands();
    List<Command> getCommands(CommandType type);

}

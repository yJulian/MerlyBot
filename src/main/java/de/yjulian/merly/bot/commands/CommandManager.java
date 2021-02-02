package de.yjulian.merly.bot.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {

    }

    @Nullable
    public Command getCommand(@NotNull String prefix) {
        for (Command command : commands) {
            if (command.name().equals(prefix)) {
                return command;
            }
        }
        return null;
    }

    private void addCommand(@NotNull Command command) {
        this.commands.add(command);
    }

}

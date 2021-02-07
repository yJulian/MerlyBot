package de.yjulian.merly.subsystem.chat;

import de.yjulian.merly.bot.MerlyBot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private final MerlyBot bot;
    private final List<CommandGroup> groups = new ArrayList<>();
    private final List<Command> commands = new ArrayList<>();

    public CommandManager(MerlyBot bot) {
        this.bot = bot;
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

    public MerlyBot getBot() {
        return bot;
    }

    /**
     * Add a command group to the instance.
     * @param group a {@link CommandGroup}.
     */
    private void addCommandGroup(@NotNull CommandGroup group) {
        List<Command> commands = group.registerCommands();
        groups.add(group);
        commands.forEach(this::addCommand);
    }

    /**
     * Add a single command to the execution list.
     *
     * @param command a {@link Command}.
     */
    private void addCommand(@NotNull Command command) {
        this.commands.add(command);
    }

}

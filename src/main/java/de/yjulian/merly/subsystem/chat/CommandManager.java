package de.yjulian.merly.subsystem.chat;

import de.yjulian.merly.ProgramState;
import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.events.EventAdapter;
import de.yjulian.merly.events.EventListener;
import de.yjulian.merly.events.ProgramStateChangedEvent;
import de.yjulian.merly.subsystem.chat.commands.HelpCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandManager implements EventAdapter {

    private final MerlyBot bot;
    private final List<CommandGroup> groups = new ArrayList<>();
    private final List<Command> commands = new ArrayList<>();

    public CommandManager(MerlyBot bot) {
        this.bot = bot;
    }

    @Nullable
    public <T extends Command> T getCommand(@NotNull String prefix, Class<T> commandType) {
        for (Command command : commands) {
            if (commandType.isInstance(command) && command.name().equals(prefix)) {
                return (T) command;
            }
        }
        return null;
    }

    @NotNull
    public List<Command> getCommands() {
        return Collections.unmodifiableList(commands);
    }

    public MerlyBot getBot() {
        return bot;
    }

    @EventListener
    public void onProgramStateChanged(ProgramStateChangedEvent event) {
        if (event.getProgramState().equals(ProgramState.POST_INIT)) {
            loadDefaultCommands();
        }
    }

    private void loadDefaultCommands() {
        addCommand(new HelpCommand());
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
     * @param command a {@link GuildCommand}.
     */
    private void addCommand(@NotNull Command command) {
        this.commands.add(command);
    }

}

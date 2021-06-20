package de.yjulian.merly.events;

import de.yjulian.merly.subsystem.command.Command;
import de.yjulian.merly.subsystem.command.CommandArguments;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

public class CommandPreExecuteEvent implements Event, Cancelable {

    private boolean canceled = false;
    private final Message message;
    private final Command command;
    private final CommandArguments arguments;

    public CommandPreExecuteEvent(Message message, Command command, CommandArguments arguments) {
        this.message = message;
        this.command = command;
        this.arguments = arguments;
    }

    @NotNull
    public Message getMessage() {
        return message;
    }

    @NotNull
    public Command getCommand() {
        return command;
    }

    @NotNull
    public CommandArguments getArguments() {
        return arguments;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }
}

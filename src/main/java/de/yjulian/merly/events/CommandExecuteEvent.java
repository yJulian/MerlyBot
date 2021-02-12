package de.yjulian.merly.events;

import de.yjulian.merly.exceptions.CommandException;
import de.yjulian.merly.subsystem.command.Command;
import de.yjulian.merly.subsystem.command.CommandArguments;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommandExecuteEvent implements Event {

    private final Message message;
    private final Command command;
    private final CommandArguments arguments;
    private final CommandException exception;

    public CommandExecuteEvent(Message message, Command command, CommandArguments arguments, CommandException exception) {
        this.message = message;
        this.command = command;
        this.arguments = arguments;
        this.exception = exception;
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

    @Nullable
    public CommandException getException() {
        return exception;
    }
}

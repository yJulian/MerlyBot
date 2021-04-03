package de.yjulian.merly.subsystem.command;

import de.yjulian.merly.util.*;
import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.events.CommandExecuteEvent;
import de.yjulian.merly.events.EventAdapter;
import de.yjulian.merly.events.EventListener;
import de.yjulian.merly.events.ProgramStateChangedEvent;
import de.yjulian.merly.exceptions.CommandException;
import de.yjulian.merly.subsystem.command.initial.HelpCommand;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManagerImpl implements CommandManager, EventAdapter {

    private static final String PREFIX = EnvUtil.getVariable("bot.prefix");
    private static final String UNKNOWN_COMMAND_MESSAGE = "No command with prefix **%s** found.";
    private static final String EXCEPTION_WITHOUT_MESSAGE = "Sorry, there was an error executing the command.";

    private final List<Command> commands = new ArrayList<>();

    @Override
    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    @Nullable
    public Command getCommand(String prefix) {
        return commands
                .stream()
                .filter(command -> command.prefix().equals(prefix))
                .findFirst()
                .orElse(null);
    }

    @Override
    @Nullable
    public Command getCommand(String prefix, CommandType type) {
        return commands
                .stream()
                .filter(command -> command.prefix().equals(prefix) && checkType(command, type))
                .findFirst()
                .orElse(null);
    }

    public List<Command> getCommands() {
        return Collections.unmodifiableList(commands);
    }

    @Override
    public List<Command> getCommands(CommandType type) {
        return commands
                .stream()
                .filter(command -> command.type().equals(type))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @EventListener
    public void onProgramStateChange(ProgramStateChangedEvent event) {
        if (event.getProgramState().equals(ProgramState.POST_INIT)) {
            registerDefault();
        }
    }

    private boolean checkType(Command command, CommandType type) {
        return command.type().equals(CommandType.GENERIC) || command.type().equals(type);
    }

    private void registerDefault() {
        addCommand(new HelpCommand());
    }

    public void call(Message messageObj, User user, Guild guild, MessageChannel messageChannel, CommandType type) {
        String message = messageObj.getContentDisplay();

        if (message.startsWith(PREFIX)) {
            message = message.substring(PREFIX.length());
            String[] data = message.split(" ");

            String prefix = data[0];
            Command command = getCommand(prefix, type);

            if (command == null) {
                messageChannel.sendMessage(String.format(UNKNOWN_COMMAND_MESSAGE, prefix)).queue();
            } else {
                try {

                    CommandArguments commandArguments = new CommandArguments(type, user, messageObj, guild, messageChannel);
                    CommandExecuteEvent firedEvent;
                    try {

                        command.onExecute(commandArguments);
                        firedEvent = new CommandExecuteEvent(messageObj, command, commandArguments, null);

                    } catch (CommandException ex) {

                        if (ex.hasPublicMessage()) {
                            messageChannel.sendMessage(ex.getPublicMessage()).queue();
                        } else {
                            messageChannel.sendMessage(EXCEPTION_WITHOUT_MESSAGE).queue();
                        }

                        firedEvent = new CommandExecuteEvent(messageObj, command, commandArguments, ex);
                    }
                    MerlyBot.getInstance().getEventManager().fireEvent(firedEvent);

                } catch (Exception ex) {
                    // On generic events (not a command event) fire the handle exception code.
                    if (type == CommandType.USER) {
                        ExceptionUtil.handleException((PrivateChannel) messageChannel, user, ex);
                    } else if (type == CommandType.GUILD) {
                        ExceptionUtil.handleException((TextChannel) messageChannel, guild.getMember(user), ex);
                    }
                    messageChannel.sendMessage(EXCEPTION_WITHOUT_MESSAGE).queue();
                }
            }
        }
    }

}

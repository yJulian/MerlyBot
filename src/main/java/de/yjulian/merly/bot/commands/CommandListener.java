package de.yjulian.merly.bot.commands;

import de.yjulian.merly.exceptions.CommandException;
import de.yjulian.merly.util.EnvUtil;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {

    private static final String UNKNOWN_COMMAND_MESSAGE = "No command with prefix **%s** found.";
    private static final String EXCEPTION_WITHOUT_MESSAGE = "Sorry, there was an error executing the command.";

    private static final String PREFIX = EnvUtil.getVariable("bot.prefix");
    private final CommandManager commandManager;

    public CommandListener(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();

        if (message.startsWith(PREFIX)) {
            message = message.substring(PREFIX.length());   // remove the prefix
            String[] evalMessage = message.split(" ");

            Command command = this.commandManager.getCommand(evalMessage[0]);

            if (command != null) {
                String[] arguments = new String[0];
                System.arraycopy(evalMessage, 1, arguments, 0, evalMessage.length - 1);
                CommandArguments commandArguments = new CommandArguments(arguments);
                try {
                    command.execute(commandArguments);
                } catch (CommandException e) {
                    if (e.hasPublicMessage()) {
                        event.getChannel().sendMessage(e.getPublicMessage()).queue();
                    } else {
                        event.getChannel().sendMessage(EXCEPTION_WITHOUT_MESSAGE).queue();
                    }
                } catch (Exception e) {
                    event.getChannel().sendMessage(EXCEPTION_WITHOUT_MESSAGE).queue();
                    e.printStackTrace();
                }
            } else {
                event.getChannel().sendMessage(String.format(UNKNOWN_COMMAND_MESSAGE, evalMessage[0])).queue();
            }
        }
    }
}

package de.yjulian.merly.subsystem.command;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {

    private final CommandManagerImpl commandManager;

    public CommandListener(CommandManagerImpl commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        commandManager.call(
                event.getMessage(),
                event.getAuthor(),
                event.getGuild(),
                event.getChannel(),
                CommandType.GUILD
        );
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        commandManager.call(event.getMessage(),
                event.getAuthor(),
                null,
                event.getChannel(),
                CommandType.USER
        );
    }

}

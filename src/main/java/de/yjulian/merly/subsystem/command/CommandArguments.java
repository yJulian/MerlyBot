package de.yjulian.merly.subsystem.command;

import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommandArguments {

    private final CommandType type;
    private final User user;
    private final MessageChannel messageChannel;
    private final Guild guild;
    private final Message message;

    public CommandArguments(CommandType type, User user, Message message, Guild guild, MessageChannel messageChannel) {
        this.type = type;
        this.user = user;
        this.messageChannel = messageChannel;
        this.guild = guild;
        this.message = message;
    }

    @NotNull
    public CommandType getType() {
        return type;
    }

    @NotNull
    public MessageChannel getMessageChannel() {
        return messageChannel;
    }

    @Nullable
    public Guild getGuild() {
        return guild;
    }

    @NotNull
    public User getUser() {
        return user;
    }

    @NotNull
    public Message getMessage() {
        return message;
    }

    @NotNull
    public String getContentDisplay() {
        return message.getContentDisplay();
    }

}

package de.yjulian.merly.subsystem.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommandArguments {

    private final CommandType type;
    private final User user;
    private final MessageChannel messageChannel;
    private final Guild guild;
    private final Message message;
    private String contentDisplay;

    public CommandArguments(CommandType type, User user, Message message, Guild guild, MessageChannel messageChannel) {
        this.type = type;
        this.user = user;
        this.messageChannel = messageChannel;
        this.guild = guild;
        this.message = message;

        this.contentDisplay = message.getContentDisplay();
    }

    public CommandArguments(CommandType type, User user, MessageChannel messageChannel, Guild guild, Message message, String contentDisplay) {
        this.type = type;
        this.user = user;
        this.messageChannel = messageChannel;
        this.guild = guild;
        this.message = message;
        this.contentDisplay = contentDisplay;
    }

    public void overrideContentDisplay(String contentDisplay) {
        this.contentDisplay = contentDisplay;
    }

    /**
     * Get the type of the event.
     * @return the Command type.
     */
    @NotNull
    public CommandType getType() {
        return type;
    }

    /**
     * Get the message channel.
     * @return a message channel.
     */
    @NotNull
    public MessageChannel getMessageChannel() {
        return messageChannel;
    }

    /**
     * Get the guild if the event was send on a guild.
     * @return the guild or null if private message.
     */
    @Nullable
    public Guild getGuild() {
        return guild;
    }

    /**
     * Get the sender from the event.
     * @return a user.
     */
    @NotNull
    public User getUser() {
        return user;
    }

    /**
     * Get the native JDA message.
     * This method should not be used because if a alias is used there is no way to distinguish if this
     * is the original message.
     *
     * See {@link #getContentDisplay()}
     * @deprecated because the {@link #getContentDisplay()} method should be used.
     * @return the raw message object.
     */
    @NotNull
    @Deprecated
    public Message getMessage() {
        return message;
    }

    /**
     * Get the JDA that is responsible for receiving the event.
     * @return a JDA instance.
     */
    @NotNull
    public JDA getJDA() {
        return user.getJDA();
    }

    /**
     * Get the content display from the message. The actual message.
     * @return a string.
     */
    @NotNull
    public String getContentDisplay() {
        return contentDisplay;
    }

}

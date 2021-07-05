package de.yjulian.merly.bot.builders;

import net.dv8tion.jda.api.entities.TextChannel;

public interface Sendable {

    /**
     * Send the object to a specific text channel.
     * @param channel the channel
     */
    void send(TextChannel channel);

    /**
     * Send the object to many channels.
     * @param channels the channels.
     */
    void bulkSend(TextChannel... channels);

}

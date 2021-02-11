package de.yjulian.merly.subsystem.chat;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public interface GuildCommand extends Command {

    void execute(@NotNull GuildMessageReceivedEvent event, @NotNull CommandArguments arguments);

}

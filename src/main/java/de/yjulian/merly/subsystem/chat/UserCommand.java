package de.yjulian.merly.subsystem.chat;

import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public interface UserCommand extends Command {

    void execute(@NotNull PrivateMessageReceivedEvent event, @NotNull CommandArguments arguments);

}

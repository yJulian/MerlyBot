package de.yjulian.merly.util;

import de.yjulian.merly.subsystem.command.CommandArguments;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public final class DiscordUtil {

    private DiscordUtil() {

    }

    public static Member getMember(Guild guild, User user) {
        return guild.getMember(user);
    }

    public static Member getMember(CommandArguments arguments) {
        Guild guild = arguments.getGuild();
        User user = arguments.getUser();

        if (guild == null) {
            throw new UnsupportedOperationException("The command arguments does not contain guild information.");
        }

        return getMember(guild, user);
    }


}

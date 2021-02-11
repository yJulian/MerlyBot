package de.yjulian.merly.subsystem.chat.commands;

import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.subsystem.chat.Command;
import de.yjulian.merly.subsystem.chat.CommandArguments;
import de.yjulian.merly.subsystem.chat.GuildCommand;
import de.yjulian.merly.subsystem.chat.HelpProvider;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class HelpCommand implements GuildCommand {

    private static final String TITLE = "Help documentation";
    private static final Color COLOR = Color.CYAN;

    @Override
    public @NotNull String name() {
        return "help";
    }

    @Override
    public void execute(@NotNull GuildMessageReceivedEvent event, @NotNull CommandArguments arguments) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(TITLE);
        builder.setColor(COLOR);
        builder.setAuthor(event.getJDA().getSelfUser().getName());

        for (Command command : MerlyBot.getCommandManager().getCommands()) {
            if (command instanceof HelpProvider) {
                HelpProvider helpProvider = (HelpProvider) command;
                builder.addField(command.name(), helpProvider.getDescription(), false);
            }
        }

        event.getChannel().sendMessage(builder.build()).queue();
    }
}

package de.yjulian.merly.subsystem.command.initial;

import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.subsystem.command.*;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class HelpCommand implements Command {

    private static final String TITLE = "Help command - %s";
    private static final Color COLOR = new Color(100, 120, 210);
    private static final int DESCRIPTION_DELIMITER = 100;

    @Override
    public String prefix() {
        return "help";
    }

    @Override
    public CommandType type() {
        return CommandType.ALL;
    }

    @Override
    public void onExecute(CommandArguments arguments) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(String.format(TITLE, arguments.getType().getName()));
        embed.setAuthor(MerlyBot.getInstance().getJDA().getSelfUser().getName());
        embed.setColor(COLOR);

        for (Command command : MerlyBot.getCommandManager().getCommands(arguments.getType())) {
            embed.addField(
                    command.prefix(),
                    command.helpProvider().getDescription(),
                    command.helpProvider().getDescription().length() <= DESCRIPTION_DELIMITER
            );
        }

        arguments.getMessageChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public HelpProvider helpProvider() {
        return HelpProvider.Builder("Shows this help screen.").build();
    }

}

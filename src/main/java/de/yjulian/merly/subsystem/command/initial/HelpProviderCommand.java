package de.yjulian.merly.subsystem.command.initial;

import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.subsystem.command.*;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class HelpProviderCommand implements GenericCommand {

    private static final String TITLE = "Help command - %s";
    private static final Color COLOR = new Color(100, 120, 210);
    private static final int DESCRIPTION_DELIMITER = 100;

    @Override
    public String prefix() {
        return "help";
    }

    @Override
    public void onExecute(CommandArguments arguments) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(String.format(TITLE, arguments.getType().getName()));
        embed.setAuthor(MerlyBot.getInstance().getJDA().getSelfUser().getName());
        embed.setColor(COLOR);

        for (Command command : MerlyBot.getCommandManager().getCommands(arguments.getType())) {
            Help help = command.helpProvider();
            if (help != null) {
                embed.addField(
                        command.prefix(), help.getDescription(),
                        help.getDescription().length() <= DESCRIPTION_DELIMITER
                );
            }
        }

        arguments.getMessageChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public Help helpProvider() {
        return Help.Builder("Shows this help screen.").build();
    }

}

package de.yjulian.merly.bot.commands;

import org.jetbrains.annotations.NotNull;

public interface Command {

    @NotNull String name();
    void execute(CommandArguments arguments);

}

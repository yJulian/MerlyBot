package de.yjulian.merly.subsystem.chat;

import org.jetbrains.annotations.NotNull;

public interface Command {

    @NotNull String name();
    void execute(@NotNull CommandArguments arguments);

}

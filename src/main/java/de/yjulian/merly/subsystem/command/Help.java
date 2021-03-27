package de.yjulian.merly.subsystem.command;

import org.jetbrains.annotations.Nullable;

public interface Help {

    /**
     * The help provider for the command.
     * This can be null. Then no help is shown in the default provider for help
     * ({@link de.yjulian.merly.subsystem.command.initial.HelpCommand}). The command is then
     * invisible.
     *
     * @return a {@link HelpProvider} or null
     */
    @Nullable
    HelpProvider helpProvider();


}

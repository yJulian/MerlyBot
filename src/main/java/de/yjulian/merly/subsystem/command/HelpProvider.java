package de.yjulian.merly.subsystem.command;

import de.yjulian.merly.subsystem.command.initial.HelpProviderCommand;
import org.jetbrains.annotations.Nullable;

public interface HelpProvider {

    /**
     * The help provider for the command.
     * This can be null. Then no help is shown in the default provider for help
     * ({@link HelpProviderCommand}). The command is then
     * invisible.
     *
     * @return a {@link Help} or null
     */
    @Nullable
    Help helpProvider();


}

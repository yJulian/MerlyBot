package de.yjulian.merly.subsystem.command;

import de.yjulian.merly.subsystem.command.initial.HelpProviderCommand;
import org.jetbrains.annotations.Nullable;

public class AliasCommand implements Command {

    private final String alias;
    private final String arguments;
    private final Command command;

    AliasCommand(String alias, String arguments, Command command) {
        this.alias = alias;
        this.arguments = arguments;
        this.command = command;
    }

    public String getAlias() {
        return alias;
    }

    public String getArguments() {
        return arguments;
    }

    public Command getCommand() {
        return command;
    }

    /**
     * The prefix for the command.
     * There are no spaces allowed in the prefix due to limitations in the command manager.
     *
     * @return a string.
     */
    @Override
    public String prefix() {
        return alias;
    }

    /**
     * The command type / When the command is executed.
     *
     * @return a {@link CommandType}.
     */
    @Override
    public CommandType type() {
        return command.type();
    }

    /**
     * The method that is called when the manager decides to execute the command.
     *
     * @param arguments the arguments
     */
    @Override
    public void onExecute(CommandArguments arguments) {
        arguments.overrideContentDisplay(this.arguments);
        command.onExecute(arguments);
    }

    /**
     * The help provider for the command.
     * This can be null. Then no help is shown in the default provider for help
     * ({@link HelpProviderCommand}). The command is then
     * invisible.
     *
     * @return a {@link Help} or null
     */
    @Override
    public @Nullable Help helpProvider() {
        return null;
    }
}

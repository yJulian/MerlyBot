package de.yjulian.merly.subsystem.command;

/**
 * Basic command implementation this should not be implemented unless you know exactly what you
 * are doing.
 *
 * @see GuildCommand for a sub interface that is only called on guild (server) related events
 * @see UserCommand for a sub interface that is called when a user excutes a command in the
 *                  private message channel.
 * @see GenericCommand for a sub interface that is called on both, server and user related
 *                  command execution.
 */
public interface Command extends Help {

    /**
     * The prefix for the command.
     * There are no spaces allowed in the prefix due to limitations in the command manager.
     *
     * @return a string.
     */
    String prefix();

    /**
     * The command type / When the command is executed.
     * @return a {@link CommandType}.
     */
    CommandType type();

    /**
     * The method that is called when the manager decides to execute the command.
     *
     * @param arguments the arguments
     */
    void onExecute(CommandArguments arguments);


}

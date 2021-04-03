package de.yjulian.merly.subsystem.command;

/**
 * Interface for slash commands.
 *
 * This interface is not fully implemented and is not going to work.
 * It is going to be finished in a future release of the engine.
 */
public interface SlashCommand extends HelpProvider {

    /**
     * Get the name/prefix from the slash command.
     * @return a non empty string.
     */
    String getName();

    /**
     * Method that is executed when the command is executed.
     * @param event the event.
     */
    void onExecute(Object event);

}

package de.yjulian.merly.subsystem.chat;

/**
 * If a command implements this interface it is listed in the help documentation.
 */
public interface HelpProvider {

    String getDescription();
    String usage();
    String errorMessage(CommandArguments arguments);

}

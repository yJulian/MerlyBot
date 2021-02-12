package de.yjulian.merly.subsystem.command;

public interface Command {

    String prefix();

    CommandType type();

    void onExecute(CommandArguments arguments);

    HelpProvider helpProvider();

}

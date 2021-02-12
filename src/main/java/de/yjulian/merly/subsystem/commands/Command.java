package de.yjulian.merly.subsystem.commands;

public interface Command {

    String prefix();
    CommandType type();
    void onExecute(CommandArguments arguments);

}

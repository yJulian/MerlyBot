package de.yjulian.merly.console;

public interface ConsoleCommand {

    String prefix();
    void execute(String[] arguments);

}

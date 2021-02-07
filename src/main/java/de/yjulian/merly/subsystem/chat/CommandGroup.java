package de.yjulian.merly.subsystem.chat;

import java.util.List;

public interface CommandGroup {

    String category();
    List<Command> registerCommands();

}

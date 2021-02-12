package de.yjulian.merly.subsystem.commands;

public interface UserCommand extends Command {

    @Override
    default CommandType type() {
        return CommandType.USER;
    }


}

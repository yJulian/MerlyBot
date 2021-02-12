package de.yjulian.merly.subsystem.command;

public interface UserCommand extends Command {

    @Override
    default CommandType type() {
        return CommandType.USER;
    }


}

package de.yjulian.merly.subsystem.command;

public interface GenericCommand extends Command {

    @Override
    default CommandType type() {
        return CommandType.GENERIC;
    }
}

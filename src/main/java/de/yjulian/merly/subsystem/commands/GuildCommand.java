package de.yjulian.merly.subsystem.commands;

public interface GuildCommand extends Command {

    @Override
    default CommandType type() {
        return CommandType.GUILD;
    }

}

package de.yjulian.merly.subsystem.command;

public interface GuildCommand extends Command {

    @Override
    default CommandType type() {
        return CommandType.GUILD;
    }

}

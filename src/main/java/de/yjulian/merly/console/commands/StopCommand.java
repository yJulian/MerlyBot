package de.yjulian.merly.console.commands;

import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.console.ConsoleCommand;

public class StopCommand implements ConsoleCommand {

    public StopCommand() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> MerlyBot.getInstance().shutdown()));
    }

    @Override
    public String prefix() {
        return "stop";
    }

    @Override
    public void execute(String[] arguments) {
        MerlyBot.getInstance().shutdown();
    }

}

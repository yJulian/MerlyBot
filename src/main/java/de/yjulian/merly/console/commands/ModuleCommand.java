package de.yjulian.merly.console.commands;

import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.console.ConsoleCommand;
import de.yjulian.merly.modules.InternalModule;

public class ModuleCommand implements ConsoleCommand {

    @Override
    public String prefix() {
        return "modules";
    }

    @Override
    public void execute(String[] arguments) {
        for (InternalModule module : MerlyBot.getInstance().getModuleManager().getModules()) {
            MerlyBot.getLogger().info(" - " + module.getData().get("name"));
        }
    }

}

package de.yjulian.merly.bot.events;

import de.yjulian.merly.bot.MerlyBot;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReadyListener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        MerlyBot.getLogger().info("JDA ready.");
    }
}

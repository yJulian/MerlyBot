package de.yjulian.merly.bot.eventslistener;

import de.yjulian.merly.events.BotReadyEvent;
import de.yjulian.merly.events.EventManager;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReadyListener extends ListenerAdapter {

    private final EventManager eventManager;

    public ReadyListener(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        this.eventManager.fireEvent(new BotReadyEvent());
    }

}

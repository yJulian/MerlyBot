package de.yjulian.merly.bot.eventslistener;

// TODO: update listener to Lavalink API
import lavalink.client.player.event.PlayerEvent;
import lavalink.client.player.event.PlayerEventAdapter;
import de.yjulian.merly.events.AudioSystemEvent;
import de.yjulian.merly.events.EventManager;

public class AudioEventListener extends PlayerEventAdapter {

    private final EventManager eventManager;

    public AudioEventListener(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void onEvent(PlayerEvent event) {
        this.eventManager.fireEvent(new AudioSystemEvent(event));
    }
}

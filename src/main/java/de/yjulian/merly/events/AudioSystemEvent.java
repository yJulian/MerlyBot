package de.yjulian.merly.events;

// TODO: replace with Lavalink player event
import lavalink.client.player.event.PlayerEvent;

public class AudioSystemEvent implements Event {

    private final PlayerEvent event;

    public AudioSystemEvent(PlayerEvent event) {
        this.event = event;
    }

    public PlayerEvent getEvent() {
        return event;
    }
}

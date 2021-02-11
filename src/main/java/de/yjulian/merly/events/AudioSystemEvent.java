package de.yjulian.merly.events;

import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;

public class AudioSystemEvent implements Event {

    private final AudioEvent event;

    public AudioSystemEvent(AudioEvent event) {
        this.event = event;
    }

    public AudioEvent getEvent() {
        return event;
    }
}

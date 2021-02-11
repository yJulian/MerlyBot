package de.yjulian.merly.bot.eventslistener;

import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import de.yjulian.merly.events.AudioSystemEvent;
import de.yjulian.merly.events.EventManager;

public class AudioEventListener extends AudioEventAdapter {

    private final EventManager eventManager;

    public AudioEventListener(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void onEvent(AudioEvent event) {
        this.eventManager.fireEvent(new AudioSystemEvent(event));
    }
}

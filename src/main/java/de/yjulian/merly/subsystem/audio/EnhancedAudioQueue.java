package de.yjulian.merly.subsystem.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioItem;

public interface EnhancedAudioQueue extends AudioQueue {

    /**
     * Add a new track with a priority. Lower priority means earlier playback.
     * @param prio a priority
     * @param item the item.
     */
    void addTrack(int prio, AudioItem item);

}

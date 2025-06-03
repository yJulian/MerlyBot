package de.yjulian.merly.subsystem.audio;

// TODO: replace with Lavalink track type
import lavalink.client.player.LavalinkTrack;

public interface EnhancedAudioQueue extends AudioQueue {

    /**
     * Add a new track with a priority. Lower priority means earlier playback.
     * @param prio a priority
     * @param item the item.
     */
    void addTrack(int prio, LavalinkTrack item);

}

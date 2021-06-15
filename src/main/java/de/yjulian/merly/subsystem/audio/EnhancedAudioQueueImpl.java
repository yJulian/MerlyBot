package de.yjulian.merly.subsystem.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.yjulian.merly.subsystem.audio.enhanced.PriorityQueue;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class EnhancedAudioQueueImpl extends AudioQueueImpl implements EnhancedAudioQueue {

    private final PriorityQueue<AudioTrack> playlist = new PriorityQueue<>();

    public EnhancedAudioQueueImpl(AudioPlayer player, VoiceChannel voiceChannel) {
        super(player, voiceChannel);
    }

    /**
     * Clear the queue.
     */
    @Override
    public void clearQueue() {
        playlist.clear();
    }

    /**
     * Add tracks to the queue.
     * Default prio 100
     *
     * @param item a audio item
     */
    @Override
    public void addTrack(AudioItem item) {
        if (item instanceof AudioTrack) {
            this.playlist.addItem(100, (AudioTrack) item);
        } else if (item instanceof AudioPlaylist) {
            for (AudioTrack track : ((AudioPlaylist) item).getTracks()) {
                this.playlist.addItem(100, track);
            }
        } else {
            throw new UnsupportedOperationException("This audio item cannot be added.");
        }
    }

    /**
     * Get the next track.
     * @return a AudioTrack
     */
    @Override
    public AudioTrack pollNextTrack() {
        return this.playlist.poll();
    }

    /**
     * Play a track instantly.
     *
     * @param track a audio track.
     */
    @Override
    public void playTrack(AudioTrack track) {
        this.playlist.addItem(0, track);
    }

    /**
     * Add a new track with a priority. Lower priority means earlier playback.
     *
     * @param prio a priority
     * @param item the item.
     */
    @Override
    public void addTrack(int prio, AudioItem item) {
        if (item instanceof AudioTrack) {
            this.playlist.addItem(prio, (AudioTrack) item);
        } else if (item instanceof AudioPlaylist) {
            for (AudioTrack track : ((AudioPlaylist) item).getTracks()) {
                this.playlist.addItem(prio, track);
            }
        } else {
            throw new UnsupportedOperationException("This audio item cannot be added.");
        }
    }
}

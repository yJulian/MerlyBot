package de.yjulian.merly.subsystem.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.function.Consumer;

public interface AudioQueue {

    /**
     * Move the audio player to a specified channel.
     *
     * @param vc the new voice channel
     */
    void moveChannel(VoiceChannel vc);

    /**
     * Skip to the next track.
     *
     * @return a boolean representing the success of the operation.
     */
    boolean nextTrack();

    /**
     * Reset the player to default settings. Volume, Queue default
     */
    void reset();

    /**
     * Get the playing state of the player.
     *
     * @return a boolean
     */
    boolean isPlaying();

    /**
     * Check if the bot is used by others or is unused in a channel.
     *
     * @return a boolean
     */
    boolean isAvailable();

    /**
     * Stop the playback
     */
    void stopTrack();

    /**
     * Clear the queue.
     */
    void clearQueue();

    /**
     * Add tracks to the queue.
     * @param item a audio item
     */
    void addTracks(AudioItem item);

    /**
     * Play a track instantly.
     * @param track a audio track.
     */
    void playTrack(AudioTrack track);

    /**
     * Get the voice channel the bot is currently.
     *
     * @return a voice channel or null.
     */
    VoiceChannel getVoiceChannel();

    /**
     * Load a track from a specific identifier. E.g. a youtube link.
     *
     * @param identifier a identifier string.
     * @param trackLoadResultConsumer a consumer getting the state of the search.
     */
    void loadTrack(String identifier, Consumer<TrackLoadResult> trackLoadResultConsumer);

}

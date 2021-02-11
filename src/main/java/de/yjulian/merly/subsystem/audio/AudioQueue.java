package de.yjulian.merly.subsystem.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.concurrent.BlockingQueue;

public class AudioQueue extends AudioEventAdapter {

    private static final int DEFAULT_VOLUME = 50;
    private static final long MIN_INACTIVITY_MS = 60000;
    private static final int DEFAULT_FRAME_BUFFER = 500;

    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;
    private final VoiceChannel voiceChannel;

    private long pausedSince = System.currentTimeMillis();

    AudioQueue(AudioPlayer player, BlockingQueue<AudioTrack> queue, VoiceChannel voiceChannel) {
        this.player = player;
        this.queue = queue;
        this.voiceChannel = voiceChannel;

        this.player.addListener(this);
        this.player.setFrameBufferDuration(DEFAULT_FRAME_BUFFER);

        AudioHandler audioHandler = new AudioHandler(this.player);
        AudioManager audioManager = this.voiceChannel.getGuild().getAudioManager();

        audioManager.openAudioConnection(voiceChannel);
        audioManager.setSendingHandler(audioHandler);

        loadDefaults();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.equals(AudioTrackEndReason.FINISHED)) {
            nextTrack();
        }
    }

    public void moveChannel(VoiceChannel vc) {
        reset();
        AudioManager audioManager = this.voiceChannel.getGuild().getAudioManager();
        audioManager.openAudioConnection(vc);
    }

    public void reset() {
        clearQueue();
        loadDefaults();
    }

    private void loadDefaults() {
        this.player.setVolume(DEFAULT_VOLUME);
    }

    /**
     * Play the next track in the queue or nothing if the queue is empty.
     *
     * @return true if a track was in the queue. False otherwise.
     */
    public boolean nextTrack() {
        AudioTrack nextTrack = queue.poll();
        if (nextTrack != null) {
            playTrack(nextTrack);
            return true;
        }
        stopTrack();
        return false;
    }

    /**
     * Check if the player is playing.
     *
     * @return true or false.
     */
    public boolean isPlaying() {
        return player.getPlayingTrack() != null;
    }

    /**
     * Check if the min inactivity duration has passed.
     * {@see #MIN_INACTIVITY_MS}
     *
     * @return a boolean.
     */
    public boolean isAvailable() {
        return pausedSince != -1 && ((System.currentTimeMillis() - pausedSince)  < MIN_INACTIVITY_MS);
    }

    /**
     * Stops the playback.
     */
    public void stopTrack() {
        player.stopTrack();
        pausedSince = System.currentTimeMillis();
    }

    /**
     * Clears the playback queue.
     */
    public void clearQueue() {
        this.queue.clear();
    }

    /**
     * Play the provided auto track instantly.
     *
     * @param track the new track to play.
     */
    public void playTrack(AudioTrack track) {
        this.player.playTrack(track);
        this.pausedSince = -1;
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public BlockingQueue<AudioTrack> getQueue() {
        return queue;
    }

    public VoiceChannel getVoiceChannel() {
        return voiceChannel;
    }
}

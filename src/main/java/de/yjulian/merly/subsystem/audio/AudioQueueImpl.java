package de.yjulian.merly.subsystem.audio;

// TODO: use Lavalink client classes
import lavalink.client.player.LavalinkPlayer;
import lavalink.client.player.event.PlayerEventAdapter;
import lavalink.client.player.LavalinkTrack;
import lavalink.client.player.LavalinkPlaylist;
import lavalink.client.player.event.TrackEndEvent;
import de.yjulian.merly.bot.MerlyBot;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.function.Consumer;

public class AudioQueueImpl extends AudioEventAdapter implements AudioQueue {

    private static final int DEFAULT_VOLUME = 50;
    private static final long MIN_INACTIVITY_MS = 60000;
    private static final int DEFAULT_FRAME_BUFFER = 500;

    private final LavalinkPlayer player;
    private final Playlist<LavalinkTrack> playlist;
    private final VoiceChannel voiceChannel;

    private long pausedSince = System.currentTimeMillis();

    AudioQueueImpl(LavalinkPlayer player, VoiceChannel voiceChannel) {
        this.player = player;
        this.playlist = new PlaylistImpl<>();
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
    public void onTrackEnd(LavalinkPlayer player, LavalinkTrack track, TrackEndEvent endReason) {
        if (true) { // TODO check end reason
            nextTrack();
        }
    }

    @Override
    public void moveChannel(VoiceChannel vc) {
        reset();
        AudioManager audioManager = this.voiceChannel.getGuild().getAudioManager();
        audioManager.openAudioConnection(vc);
    }

    @Override
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
    @Override
    public boolean nextTrack() {
        LavalinkTrack nextTrack = pollNextTrack();
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
    @Override
    public boolean isPlaying() {
        // TODO: adjust according to Lavalink API
        return false;
    }

    /**
     * Check if the min inactivity duration has passed.
     * {@see #MIN_INACTIVITY_MS}
     *
     * @return a boolean.
     */
    @Override
    public boolean isAvailable() {
        return pausedSince != -1 && ((System.currentTimeMillis() - pausedSince)  < MIN_INACTIVITY_MS);
    }

    /**
     * Stops the playback.
     */
    @Override
    public void stopTrack() {
        // TODO: stop playback in Lavalink
        pausedSince = System.currentTimeMillis();
    }

    /**
     * Clears the playback queue.
     */
    @Override
    public void clearQueue() {
        this.playlist.clear();
    }

    @Override
    public void addTrack(LavalinkTrack item) {
        if (item instanceof LavalinkTrack) {
            this.playlist.addTrack(Priority.MEDIUM, item);
        } else if (item instanceof LavalinkPlaylist) {
            for (LavalinkTrack track : ((LavalinkPlaylist) item).getTracks()) {
                this.playlist.addTrack(Priority.MEDIUM, track);
            }
        } else {
            throw new UnsupportedOperationException("This audio item cannot be added.");
        }
    }

    /**
     * Play the provided auto track instantly.
     *
     * @param track the new track to play.
     */
    @Override
    public void playTrack(LavalinkTrack track) {
        // TODO: play track via Lavalink
        this.pausedSince = -1;
    }

    public LavalinkPlayer getPlayer() {
        return player;
    }

    public Playlist<LavalinkTrack> getPlaylist() {
        return this.playlist;
    }

    @Override
    public VoiceChannel getVoiceChannel() {
        return voiceChannel;
    }

    /**
     * Get and remove the head of the playlist.
     *
     * @return a LavalinkTrack or null.
     */
    @Override
    public LavalinkTrack pollNextTrack() {
        return this.playlist.poll();
    }

    @Override
    public void loadTrack(String identifier, Consumer<TrackLoadResult> resultConsumer) {
        MerlyBot.getInstance().getAudioManager().getTrack(identifier, this::addTrack, resultConsumer);
    }

}

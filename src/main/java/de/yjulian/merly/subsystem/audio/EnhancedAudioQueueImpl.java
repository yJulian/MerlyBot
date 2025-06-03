package de.yjulian.merly.subsystem.audio;

// TODO: use Lavalink player classes
import lavalink.client.player.LavalinkPlayer;
import lavalink.client.player.LavalinkTrack;
import lavalink.client.player.LavalinkPlaylist;
import de.yjulian.merly.subsystem.audio.enhanced.PriorityQueue;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class EnhancedAudioQueueImpl extends AudioQueueImpl implements EnhancedAudioQueue {

    private final PriorityQueue<LavalinkTrack> playlist = new PriorityQueue<>();

    public EnhancedAudioQueueImpl(LavalinkPlayer player, VoiceChannel voiceChannel) {
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
    public void addTrack(LavalinkTrack item) {
        // TODO: adapt to Lavalink track/playlist API
        if (item instanceof LavalinkTrack) {
            this.playlist.addItem(100, item);
        } else if (item instanceof LavalinkPlaylist) {
            for (LavalinkTrack track : ((LavalinkPlaylist) item).getTracks()) {
                this.playlist.addItem(100, track);
            }
        } else {
            throw new UnsupportedOperationException("This audio item cannot be added.");
        }
    }

    /**
     * Get the next track.
     * @return a LavalinkTrack
     */
    @Override
    public LavalinkTrack pollNextTrack() {
        return this.playlist.poll();
    }

    /**
     * Play a track instantly.
     *
     * @param track a audio track.
     */
    @Override
    public void playTrack(LavalinkTrack track) {
        this.playlist.addItem(0, track);
    }

    /**
     * Add a new track with a priority. Lower priority means earlier playback.
     *
     * @param prio a priority
     * @param item the item.
     */
    @Override
    public void addTrack(int prio, LavalinkTrack item) {
        if (item instanceof LavalinkTrack) {
            this.playlist.addItem(prio, item);
        } else if (item instanceof LavalinkPlaylist) {
            for (LavalinkTrack track : ((LavalinkPlaylist) item).getTracks()) {
                this.playlist.addItem(prio, track);
            }
        } else {
            throw new UnsupportedOperationException("This audio item cannot be added.");
        }
    }
}

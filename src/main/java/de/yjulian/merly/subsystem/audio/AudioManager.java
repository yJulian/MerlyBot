package de.yjulian.merly.subsystem.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.yjulian.merly.exceptions.NoBotAvailableException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class AudioManager {

    private final AudioPlayerManager manager;
    private final HashMap<Guild, AudioQueue> audioQueues = new HashMap<>();

    public AudioManager() {
        this.manager = new DefaultAudioPlayerManager();
    }

    /**
     * Get a audio queue for the voice channel provided. If no player is available the method with throw
     * a {@link NoBotAvailableException}.
     *
     * @param voiceChannel the voice channel to join.
     * @throws NoBotAvailableException when no bot is available.
     * @return a {@link AudioQueueImpl} representing the player.
     */
    @NotNull
    public AudioQueue getAudioQueue(VoiceChannel voiceChannel) {
        Guild guild = voiceChannel.getGuild();
        if (audioQueues.containsKey(guild)) {
            AudioQueue audioQueue = audioQueues.get(guild);

            if (audioQueue.getVoiceChannel().equals(voiceChannel)) {
                return audioQueue;
            } else {
                if (audioQueue.isAvailable()) {
                    audioQueue.moveChannel(voiceChannel);
                    return audioQueue;
                } else {
                    throw new NoBotAvailableException("There is no bot currently available.");
                }
            }
        }

        LinkedBlockingQueue<AudioTrack> queue = new LinkedBlockingQueue<>();
        AudioPlayer player = manager.createPlayer();

        AudioQueueImpl audioQueue = new AudioQueueImpl(player, queue, voiceChannel);
        audioQueues.put(guild, audioQueue);
        return audioQueue;
    }

    void getTrack(String identifier, Consumer<AudioItem> itemConsumer) {
        manager.loadItem(identifier, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                itemConsumer.accept(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                itemConsumer.accept(playlist);
            }

            @Override
            public void noMatches() {
                itemConsumer.accept(null);
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                itemConsumer.accept(null);
            }
        });
    }

}

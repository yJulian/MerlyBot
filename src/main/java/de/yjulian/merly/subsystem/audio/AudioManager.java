package de.yjulian.merly.subsystem.audio;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.exceptions.BotUnavailableException;
import de.yjulian.merly.util.EnumUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Consumer;

public class AudioManager {

    private final AudioPlayerManager manager;
    private final HashMap<Guild, AudioQueue> audioQueues = new HashMap<>();

    public AudioManager() {
        this.manager = new DefaultAudioPlayerManager();
        init();
    }

    private void init() {
        MerlyBot.getLogger().info("Initializing Merly Bot - Audio Manager");
        AudioConfiguration.ResamplingQuality resamplingQuality = EnumUtils
                .getOrDefault(AudioConfiguration.ResamplingQuality.class,
                        System.getenv("MERLY_RESAMPLING_QUALITY"),
                        AudioConfiguration.ResamplingQuality.MEDIUM
                );
        MerlyBot.getLogger().info(String.format("Audio Resampling Quality: %s", resamplingQuality.name()));

        MerlyBot.getLogger().info("Initializing Merly Bot - Finished");
        setCurrentQuality(resamplingQuality);
    }

    public AudioConfiguration.ResamplingQuality getCurrentQuality() {
        return getConfiguration().getResamplingQuality();
    }

    public void setCurrentQuality(AudioConfiguration.ResamplingQuality quality) {
        getConfiguration().setResamplingQuality(quality);
        MerlyBot.getLogger().debug(String.format("Audio Resampling Quality updated to %s", quality.name()));
    }

    /**
     * Get the current audio configuration.
     * @return the current configuration.
     */
    public AudioConfiguration getConfiguration() {
        return this.manager.getConfiguration();
    }

    /**
     * Get a audio queue for the voice channel provided. If no player is available the method with throw
     * a {@link BotUnavailableException}.
     *
     * @param voiceChannel the voice channel to join.
     * @throws BotUnavailableException when no bot is available.
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
                    throw new BotUnavailableException("There is no bot currently available.");
                }
            }
        }

        AudioPlayer player = manager.createPlayer();

        AudioQueueImpl audioQueue = new AudioQueueImpl(player, voiceChannel);
        audioQueues.put(guild, audioQueue);
        return audioQueue;
    }

    void getTrack(String identifier, Consumer<AudioItem> itemConsumer, Consumer<TrackLoadResult> result) {
        manager.loadItem(identifier, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                itemConsumer.accept(track);
                result.accept(new TrackLoadResult(null, TrackLoadResult.State.TRACK, track));
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                itemConsumer.accept(playlist);
                result.accept(new TrackLoadResult(null, TrackLoadResult.State.PLAYLIST, playlist));
            }

            @Override
            public void noMatches() {
                itemConsumer.accept(null);
                result.accept(new TrackLoadResult(null, TrackLoadResult.State.NOTHING_FOUND, null));
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                itemConsumer.accept(null);
                result.accept(new TrackLoadResult(exception, TrackLoadResult.State.EXCEPTION, null));
            }
        });
    }

}

package de.yjulian.merly.subsystem.audio;

// TODO: Replace lavaplayer imports with lavalink client equivalents
import lavalink.client.player.LavalinkPlayer;
import lavalink.client.player.event.PlayerEvent;
import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.exceptions.BotUnavailableException;
import de.yjulian.merly.util.EnumUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Consumer;

public class AudioManager {

    // TODO: use appropriate Lavalink client class
    private final LavalinkPlayer manager;
    private final HashMap<Guild, AudioQueue> audioQueues = new HashMap<>();

    public AudioManager() {
        // TODO: initialize Lavalink player
        this.manager = null;
        init();
    }

    private void init() {
        MerlyBot.getLogger().info("Initializing Merly Bot - Audio Manager");
        // TODO: configure Lavalink connection
        MerlyBot.getLogger().info("Initializing Merly Bot - Finished");
    }

    // TODO: implement Lavalink audio configuration methods if needed

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

        // TODO: create Lavalink player instance
        LavalinkPlayer player = manager;

        AudioQueueImpl audioQueue = new AudioQueueImpl(player, voiceChannel);
        audioQueues.put(guild, audioQueue);
        return audioQueue;
    }

    // TODO: implement track loading via Lavalink client
    void getTrack(String identifier, Consumer<Object> itemConsumer, Consumer<TrackLoadResult> result) {
        // Implementation pending
    }

}

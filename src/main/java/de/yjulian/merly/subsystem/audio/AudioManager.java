package de.yjulian.merly.subsystem.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.yjulian.merly.exceptions.NoBotAvailableException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class AudioManager {

    private final AudioPlayerManager manager;
    private final HashMap<Guild, AudioQueue> audioQueues = new HashMap<>();

    public AudioManager() {
        this.manager = new DefaultAudioPlayerManager();
    }

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

        AudioQueue audioQueue = new AudioQueue(player, queue, voiceChannel);
        audioQueues.put(guild, audioQueue);
        return audioQueue;
    }

}

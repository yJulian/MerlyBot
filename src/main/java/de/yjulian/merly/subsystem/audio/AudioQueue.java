package de.yjulian.merly.subsystem.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.VoiceChannel;

public interface AudioQueue {

    void moveChannel(VoiceChannel vc);

    boolean nextTrack();

    void reset();

    boolean isPlaying();

    boolean isAvailable();

    void stopTrack();

    void clearQueue();

    void playTrack(AudioTrack track);

    VoiceChannel getVoiceChannel();

}

package de.yjulian.merly.util;

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchMusicProvider;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public final class AudioUtil {

    private static YoutubeSearchProvider youtubeSearchProvider;
    private static YoutubeSearchMusicProvider youtubeSearchMusicProvider;
    private static YoutubeAudioSourceManager youtubeAudioSourceManager;

    private AudioUtil() {

    }

    public static AudioItem youtubeSearch(String query) {
        if (youtubeSearchProvider == null) {
            youtubeSearchProvider = new YoutubeSearchProvider();
        }

        return youtubeSearchProvider.loadSearchResult(query, info -> {
            if (youtubeAudioSourceManager == null) {
                youtubeAudioSourceManager = new YoutubeAudioSourceManager();
            }
            return new YoutubeAudioTrack(info, youtubeAudioSourceManager);
        });
    }

    public static AudioItem youtubeMusicSearch(String query) {
        if (youtubeSearchMusicProvider == null) {
            youtubeSearchMusicProvider = new YoutubeSearchMusicProvider();
        }

        return youtubeSearchMusicProvider.loadSearchMusicResult(query, info -> {
            if (youtubeAudioSourceManager == null) {
                youtubeAudioSourceManager = new YoutubeAudioSourceManager();
            }
            return new YoutubeAudioTrack(info, youtubeAudioSourceManager);
        });
    }

    public static AudioTrack cloneAudioTrack(AudioTrack track) {
        return track.makeClone();
    }

}

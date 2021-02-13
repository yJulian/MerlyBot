package de.yjulian.merly.subsystem.audio;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TrackLoadResult {

    private final FriendlyException exception;
    private final State state;
    private final AudioItem item;

    TrackLoadResult(FriendlyException exception, State state, AudioItem item) {
        this.exception = exception;
        this.state = state;
        this.item = item;
    }

    @Nullable
    public FriendlyException getException() {
        return exception;
    }

    @NotNull
    public State getState() {
        return state;
    }

    @Nullable
    public AudioItem getItem() {
        return item;
    }

    @Nullable
    public enum State {
        TRACK, PLAYLIST, NOTHING_FOUND, EXCEPTION
    }

}

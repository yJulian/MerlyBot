package de.yjulian.merly.subsystem.audio;

// TODO: replace with Lavalink classes
import lavalink.client.player.event.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TrackLoadResult {

    private final Exception exception;
    private final State state;
    private final Object item;

    TrackLoadResult(Exception exception, State state, Object item) {
        this.exception = exception;
        this.state = state;
        this.item = item;
    }

    @Nullable
    public Exception getException() {
        return exception;
    }

    @NotNull
    public State getState() {
        return state;
    }

    @Nullable
    public Object getItem() {
        return item;
    }

    @Nullable
    public enum State {
        TRACK, PLAYLIST, NOTHING_FOUND, EXCEPTION
    }

}

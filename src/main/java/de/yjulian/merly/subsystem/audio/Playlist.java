package de.yjulian.merly.subsystem.audio;

public interface Playlist<T> {

    void addTrack(Priority priority, T entry);

    T poll();

    void clear();

    boolean remove(T entry);

}

package de.yjulian.merly.subsystem.audio;

import java.util.HashMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class PlaylistImpl<T> implements Playlist<T> {

    private final HashMap<Priority, BlockingDeque<T>> deque = new HashMap<>();

    @Override
    public void addTrack(Priority priority, T entry) {
        BlockingDeque<T> queue = deque.getOrDefault(priority, new LinkedBlockingDeque<>());

        queue.addLast(entry);

        deque.put(priority, queue);
    }

    @Override
    public T poll() {
        for (BlockingDeque<T> queue : deque.values()) {
            if (!queue.isEmpty()) {
                return queue.poll();
            }
        }
        return null;
    }

    @Override
    public void clear() {
        deque.clear();
    }

    @Override
    public boolean remove(T entry) {
        boolean removed = false;

        for (BlockingDeque<T> queue : deque.values()) {
            removed |= queue.remove(entry);
        }

        return removed;
    }
}

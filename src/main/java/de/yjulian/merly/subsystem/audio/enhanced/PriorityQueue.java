package de.yjulian.merly.subsystem.audio.enhanced;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PriorityQueue<T> {

    private final Set<PriorityQueueEntry<T>> playlist = new TreeSet<>();

    /**
     * Add a item to the queue.
     * @param prio the priority lower means better.
     * @param item the item
     */
    public void addItem(int prio, T item) {
        PriorityQueueEntry<T> next = getEntry(prio);
        if (next == null) {
            next = new PriorityQueueEntry<>(prio);
            playlist.add(next);
        }
        next.addTrack(item);
    }

    /**
     * Get the internal entry at a specific priority or null if no priority with the prio
     * is available.
     * @param prio the prio
     * @return a {@link PriorityQueueEntry<T>} or null.
     */
    private PriorityQueueEntry<T> getEntry(int prio) {
        Iterator<PriorityQueueEntry<T>> iterator = playlist.iterator();
        PriorityQueueEntry<T> next = null;
        while (iterator.hasNext()) {
            PriorityQueueEntry<T> temp = iterator.next();
            if (temp.getPriority() > prio)
                break;
            next = temp;
        }
        return next;
    }

    /**
     * Poll the element with the highest prio.
     * @return a item or null if empty.
     */
    public T poll() {
        for (PriorityQueueEntry<T> entry : playlist) {
            T track = entry.poll();
            if (track != null) {
                return track;
            }
        }
        return null;
    }

    /**
     * Clear the queue.
     */
    public void clear() {
        playlist.clear();
    }

    /**
     * The internal datastructures to hold the items.
     * @param <T> the data type.
     */
    private static final class PriorityQueueEntry<T> implements Comparable<PriorityQueueEntry<T>> {

        private final int prio;
        private final Queue<T> items;

        /**
         * Entry
         * @param prio the prio.
         */
        public PriorityQueueEntry(int prio) {
            this.prio = prio;
            this.items = new LinkedList<>();
        }

        /**
         * Get the priority
         * @return a int.
         */
        public int getPriority() {
            return prio;
        }

        /**
         * Add a item to the entry.
         * @param item the item to add.
         */
        public void addTrack(T item) {
            items.add(item);
        }

        /**
         * Poll the first entry.
         * @return a item or null.
         */
        public T poll() {
            return items.poll();
        }

        /**
         * Compare method.
         * @param o the compare to object.
         * @return a int
         */
        @Override
        public int compareTo(@NotNull PriorityQueueEntry o) {
            return prio - o.prio;
        }
    }

}

package de.yjulian.merly.events;

public class EventFutur<T extends Event> {

    private final T event;
    private final int executedAmount;

    public EventFutur(T event, int executedAmount) {
        this.event = event;
        this.executedAmount = executedAmount;
    }

    public T getEvent() {
        return event;
    }

    public int getExecutedAmount() {
        return executedAmount;
    }
}
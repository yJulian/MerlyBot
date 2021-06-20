package de.yjulian.merly.events;

public interface Cancelable {

    void setCanceled(boolean canceled);

    boolean isCanceled();

}

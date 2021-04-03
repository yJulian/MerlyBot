package de.yjulian.merly.events;

import de.yjulian.merly.data.Malfunction;

public class MalfunctionOccurredEvent implements Event {

    private final Malfunction malfunction;

    public MalfunctionOccurredEvent(Malfunction malfunction) {
        this.malfunction = malfunction;
    }

    public Malfunction getMalfunction() {
        return malfunction;
    }
}

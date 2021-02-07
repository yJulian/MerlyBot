package de.yjulian.merly.events;

import de.yjulian.merly.ProgramState;

public class ProgramStateChangedEvent implements Event {

    private final ProgramState previousProgramState;
    private final ProgramState programState;

    public ProgramStateChangedEvent(ProgramState previousProgramState, ProgramState programState) {
        this.previousProgramState = previousProgramState;
        this.programState = programState;
    }

    public ProgramState getPreviousProgramState() {
        return previousProgramState;
    }

    public ProgramState getProgramState() {
        return programState;
    }
}

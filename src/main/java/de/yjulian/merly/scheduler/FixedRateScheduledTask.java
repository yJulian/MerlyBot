package de.yjulian.merly.scheduler;

public interface FixedRateScheduledTask extends ScheduledTask {

    /**
     * Get the period between two executes.
     * This number has to be static. No changes are detected by the scheduler.
     * @return a long
     */
    long getPeriod();

}

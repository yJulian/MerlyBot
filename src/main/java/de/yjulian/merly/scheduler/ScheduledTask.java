package de.yjulian.merly.scheduler;

import java.util.concurrent.TimeUnit;

public interface ScheduledTask extends Runnable {

    /**
     * The initial delay for the execute.
     * @return a long.
     */
    long getDelay();

    /**
     * The time unit for the delay.
     * @return a {@link TimeUnit}
     */
    TimeUnit getTimeUnit();

}

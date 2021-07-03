package de.yjulian.merly.subsystem.service;

import de.yjulian.merly.scheduler.FixedRateScheduledTask;

import java.util.concurrent.TimeUnit;

class ServiceAdapter implements FixedRateScheduledTask {

    private final Service service;

    public ServiceAdapter(Service service) {
        this.service = service;
    }

    /**
     * Get the period between two executes.
     * This number has to be static. No changes are detected by the scheduler.
     *
     * @return a long
     */
    @Override
    public long getPeriod() {
        return service.serviceDelayMs();
    }

    /**
     * The initial delay for the execute.
     *
     * @return a long.
     */
    @Override
    public long getDelay() {
        return service.initialDelayMs();
    }

    /**
     * The time unit for the delay.
     *
     * @return a {@link TimeUnit}
     */
    @Override
    public TimeUnit getTimeUnit() {
        return TimeUnit.MILLISECONDS;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        service.onServiceExecute();
    }
}

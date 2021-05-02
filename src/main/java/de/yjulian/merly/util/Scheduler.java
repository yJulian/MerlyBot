package de.yjulian.merly.util;

import de.yjulian.merly.bot.MerlyBot;

import java.util.concurrent.*;

public class Scheduler {

    private final ScheduledExecutorService ses;
    private final ExecutorService es;

    /**
     * Initialize a new Scheduler with a specific thread count.
     *
     * @param scheduledCoreSize the amount of parallel scheduled tasks.
     * @param executorCoreSize the amount of parallel tasks.
     */
    public Scheduler(final int scheduledCoreSize, final int executorCoreSize) {
        this.ses = Executors.newScheduledThreadPool(scheduledCoreSize);
        this.es = Executors.newFixedThreadPool(executorCoreSize);
    }

    /**
     * Get the scheduler instance to add new events.
     *
     * @return the main scheduler instance.
     */
    public static Scheduler getInstance() {
        return MerlyBot.getInstance().getScheduler();
    }

    /**
     * Shutdown the scheduler
     */
    public void shutdown() {
        this.ses.shutdown();
        this.es.shutdown();
    }

    /**
     * Execute a new runnable in an async context.
     * @param task the task that is going to be executed.
     * @return the future from the ExecutorService
     */
    public Future<?> execute(Runnable task) {
        return es.submit(task);
    }

    public ScheduledFuture<?> scheduledExecute(Runnable task, long delay, TimeUnit unit) {
        return ses.schedule(task, delay, unit);
    }

    /**
     * Schedule a task at a fixed rate.

     * @param task the task to execute.
     * @param initialDelay the initial delay.
     * @param period the period between two executes.
     * @param unit the time unit.
     * @return a scheduled future.
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return ses.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

}

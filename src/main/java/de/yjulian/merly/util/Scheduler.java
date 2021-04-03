package de.yjulian.merly.util;

import de.yjulian.merly.bot.MerlyBot;

import java.util.concurrent.*;

public class Scheduler {

    private final static int SCHEDULED_CORE_SIZE = 4;
    private final static int EXECUTOR_CORE_SIZE = 8;

    private final ScheduledExecutorService ses;
    private final ExecutorService es;

    public Scheduler() {
        this.ses = Executors.newScheduledThreadPool(SCHEDULED_CORE_SIZE);
        this.es = Executors.newFixedThreadPool(EXECUTOR_CORE_SIZE);
    }

    public static Scheduler getInstance() {
        return MerlyBot.getInstance().getScheduler();
    }

    public Future<?> execute(Runnable task) {
        return es.submit(task);
    }

    public ScheduledFuture<?> scheduledExecute(Runnable task, long delay, TimeUnit unit) {
        return ses.schedule(task, delay, unit);
    }

}

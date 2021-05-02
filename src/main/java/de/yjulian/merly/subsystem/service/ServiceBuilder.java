package de.yjulian.merly.subsystem.service;

import de.yjulian.merly.bot.MerlyBot;

public class ServiceBuilder {

    private final Runnable execute;
    private long period;
    private long delay;

    public ServiceBuilder(long period, Runnable execute) {
        this.period = period;
        this.execute = execute;
    }

    public ServiceBuilder setPeriod(long period) {
        this.period = period;
        return this;
    }

    public ServiceBuilder setInitialDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public Service build() {
        return new Service() {
            @Override
            public long initialDelayMs() {
                return delay;
            }

            @Override
            public long serviceDelayMs() {
                return delay;
            }

            @Override
            public void onServiceExecute() {
                execute.run();
            }
        };
    }

    public Service buildAndRegister() {
        Service service = build();

        MerlyBot.getInstance().getServiceManager().registerService(service);

        return service;
    }

}

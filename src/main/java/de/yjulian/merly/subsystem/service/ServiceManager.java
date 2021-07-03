package de.yjulian.merly.subsystem.service;

import de.yjulian.merly.bot.MerlyBot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ServiceManager {

    private final List<ServiceData> services = new ArrayList<>();

    public void registerService(@NotNull Service service) {
        ScheduledFuture<?> sf = MerlyBot.getInstance().getScheduler().scheduleAtFixedRate(new ServiceAdapter(service));

        services.add(new ServiceData(service, sf));
    }

    public boolean removeService(@NotNull Service service) {
        ServiceData serviceData = findService(service);
        if (serviceData != null) {
            serviceData.getFuture().cancel(true);
            return true;
        }
        return false;
    }

    private ServiceData findService(Service service) {
        Optional<ServiceData> serviceDataOptional = services.stream().filter(serviceData -> serviceData.getService() == service).findFirst();

        return serviceDataOptional.orElse(null);
    }

    private record ServiceData(Service service, ScheduledFuture<?> future) {

        public Service getService() {
            return service;
        }

        public ScheduledFuture<?> getFuture() {
            return future;
        }
    }

}

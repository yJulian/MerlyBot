package de.yjulian.merly.events;

import de.yjulian.merly.bot.MerlyBot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class EventManager {

    private final List<EventAdapter> adapters = new ArrayList<>();

    public EventManager() {

    }

    public void addEventAdapter(EventAdapter adapter, EventAdapter... additions) {
        this.adapters.add(adapter);
        this.adapters.addAll(Arrays.asList(additions));
    }

    public void fireEventAsync(Event event, Consumer<Integer> amount) {
        MerlyBot.getInstance().getScheduler().execute(() -> {
            int firedEvents = fireEvent(event);
            if (amount != null) {
                amount.accept(firedEvents);
            }
        });
    }

    public int fireEvent(Event event) {
        int executeCount = 0;
        for (EventAdapter adapter : adapters) {
            List<Method> methods = new ArrayList<>();
            for (Method method : adapter.getClass().getMethods()) {
                if (method.isAnnotationPresent(EventListener.class)) {
                    if (method.getParameterCount() == 1) {
                        if (method.getParameterTypes()[0].equals(event.getClass())) {
                            methods.add(method);
                        }
                    } else {
                        String warning = String.format(
                                "Found invalid method in %s. " +
                                        "Invalid method parameters at method %s. " +
                                        "Excepted 1 method but got %s parameters.",
                                adapter.getClass().getName(),
                                method.getName(),
                                method.getParameterCount()
                        );
                        MerlyBot.getLogger().warn(warning);
                    }
                }
            }

            methods.sort(Comparator.comparingInt(o -> o.getAnnotation(EventListener.class).priority()));

            for (Method method : methods) {
                try {
                    method.invoke(adapter, event);
                    executeCount++;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }
        return executeCount;
    }

}

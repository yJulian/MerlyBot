package de.yjulian.merly.events;

import net.dv8tion.jda.api.JDABuilder;

public class JDABuildEvent implements Event {

    private JDABuilder jdaBuilder;

    public JDABuildEvent(JDABuilder jdaBuilder) {
        this.jdaBuilder = jdaBuilder;
    }

    public JDABuilder getJDABuilder() {
        return jdaBuilder;
    }
}

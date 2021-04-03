package de.yjulian.merly.events;

import net.dv8tion.jda.api.JDABuilder;

import java.util.function.Function;

public class JDABuildEvent implements Event {

    private JDABuilder jdaBuilder;

    public JDABuildEvent(JDABuilder jdaBuilder) {
        this.jdaBuilder = jdaBuilder;
    }

    /**
     * Apply a new method to the jda builder.
     *
     * @param function a function to handle the editing.
     */
    public void apply(Function<JDABuilder, JDABuilder> function) {
        this.jdaBuilder = function.apply(this.jdaBuilder);
    }

    /**
     * Get the last modified jda builder.
     * @return a jda builder.
     */
    public JDABuilder getJDABuilder() {
        return jdaBuilder;
    }
}

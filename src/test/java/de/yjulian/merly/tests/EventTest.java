package de.yjulian.merly.tests;

import de.yjulian.merly.events.*;
import de.yjulian.merly.events.BotReadyEvent;
import de.yjulian.merly.events.CommandExecuteEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest implements EventAdapter {

    private static EventManager manager;

    @BeforeAll
    static void initialize() {
        manager = new EventManager();
        manager.addEventAdapter(new EventTest());
    }

    @Test
    public void callBotReady() {
        assertEquals(2, manager.fireEvent(new BotReadyEvent()));
    }

    @Test
    public void callCommandEvent() {
        assertEquals(1, manager.fireEvent(new CommandExecuteEvent(null, null, null, null)));
    }

    @EventListener()
    public void onBotReady(BotReadyEvent event) {
    }

    @EventListener()
    public void onBotReady2(BotReadyEvent event) {
    }

    @EventListener
    public void onCommandEvent(CommandExecuteEvent event) {
    }

}
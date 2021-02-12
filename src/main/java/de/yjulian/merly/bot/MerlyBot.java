package de.yjulian.merly.bot;

import de.yjulian.merly.ProgramState;
import de.yjulian.merly.events.EventAdapter;
import de.yjulian.merly.subsystem.audio.AudioManager;
import de.yjulian.merly.events.EventManager;
import de.yjulian.merly.bot.eventslistener.ReadyListener;
import de.yjulian.merly.events.ProgramStateChangedEvent;
import de.yjulian.merly.modules.ModuleManager;
import de.yjulian.merly.subsystem.commands.CommandListener;
import de.yjulian.merly.subsystem.commands.CommandManager;
import de.yjulian.merly.subsystem.commands.CommandManagerImpl;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MerlyBot {

    private static final Logger LOGGER = LoggerFactory.getLogger("de.yjulian.merly");
    private static MerlyBot instance;

    private final String token;
    private JDA jda;

    private final EventManager eventManager;
    private ModuleManager moduleManager;
    private CommandManagerImpl commandManager;
    private ProgramState currentProgramState = ProgramState.STARTUP;
    private AudioManager audioManager;

    public MerlyBot(String token) throws Exception {
        instance = this;

        this.token = token;

        // Initialized before pre init to fire the events.
        this.eventManager = new EventManager();
        preInit();
        init();
        postInit();
        setProgramState(ProgramState.RUNNING);
    }

    public void shutdown() {
        setProgramState(ProgramState.SHUTDOWN);
        this.jda.shutdown();
    }

    private void setProgramState(ProgramState newState) {
        this.eventManager.fireEvent(new ProgramStateChangedEvent(currentProgramState, newState));
        this.currentProgramState = newState;
    }

    private void preInit() {
        setProgramState(ProgramState.PRE_INIT);

        this.moduleManager = new ModuleManager();
        this.commandManager = new CommandManagerImpl();

        this.eventManager.addEventAdapter(this.moduleManager);
        this.eventManager.addEventAdapter(this.commandManager);
    }

    private void init() {
        setProgramState(ProgramState.INIT);

        this.audioManager = new AudioManager();
    }

    private void postInit() throws Exception {
        setProgramState(ProgramState.POST_INIT);

        this.jda = JDABuilder
                .create(token, GatewayIntent.getIntents(GatewayIntent.DEFAULT))
                .build();

        this.jda.addEventListener(
                new ReadyListener(this.eventManager),
                new CommandListener(this.commandManager)
        );
    }

    public static CommandManager getCommandManager() {
        return getInstance().commandManager;
    }

    public static MerlyBot getInstance() {
        return instance;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public JDA getJDA() {
        return jda;
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}

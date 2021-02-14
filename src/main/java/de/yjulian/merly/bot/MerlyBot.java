package de.yjulian.merly.bot;

import de.yjulian.merly.util.ProgramState;
import de.yjulian.merly.console.ConsoleManager;
import de.yjulian.merly.subsystem.audio.AudioManager;
import de.yjulian.merly.events.EventManager;
import de.yjulian.merly.bot.eventslistener.ReadyListener;
import de.yjulian.merly.events.ProgramStateChangedEvent;
import de.yjulian.merly.modules.ModuleManager;
import de.yjulian.merly.subsystem.command.CommandListener;
import de.yjulian.merly.subsystem.command.CommandManager;
import de.yjulian.merly.subsystem.command.CommandManagerImpl;
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
    private ConsoleManager consoleManager;

    /**
     * Create a new instance from the MerlyBot
     *
     * @param token the discord bot token.
     * @throws Exception an general exception.
     */
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

    /**
     * Method to shutdown the bot with all of the components.
     */
    public void shutdown() {
        LOGGER.info("Shutting down.");
        setProgramState(ProgramState.SHUTDOWN);
        this.jda.shutdown();
    }

    /**
     * Method to set the new program state {@link ProgramState}
     *
     * @param newState the new state. e.g. PostInit, Init, PreInit
     */
    private void setProgramState(ProgramState newState) {
        this.eventManager.fireEvent(new ProgramStateChangedEvent(currentProgramState, newState));
        this.currentProgramState = newState;
    }

    /**
     * Method to handle the pre initialization.
     */
    private void preInit() {
        setProgramState(ProgramState.PRE_INIT);

        this.moduleManager = new ModuleManager();
        this.consoleManager = new ConsoleManager(System.in);
        this.commandManager = new CommandManagerImpl();

        this.eventManager.addEventAdapter(this.moduleManager, this.consoleManager, this.commandManager);
    }

    /**
     * Method to handle the initialization.
     */
    private void init() {
        setProgramState(ProgramState.INIT);

        this.audioManager = new AudioManager();
    }

    /**
     * Method to handle the post initialization.
     *
     * @throws Exception an exception.
     */
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

    /**
     * Get the command manager to register new commands or get the registered ones.
     *
     * @return a {@link CommandManager} initialized for the current instance.
     */
    public static CommandManager getCommandManager() {
        return getInstance().commandManager;
    }

    /**
     * Get the current instance from the bot.
     *
     * @return the active instance.
     */
    public static MerlyBot getInstance() {
        return instance;
    }

    /**
     * Get the console manager.
     *
     * @return a {@link ConsoleManager}
     */
    public ConsoleManager getConsoleManager() {
        return consoleManager;
    }

    /**
     * Get the module Manager
     * @return a {@link ModuleManager}
     */
    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    /**
     * Get the event Manager to call custom events or register new {@link de.yjulian.merly.events.EventAdapter}
     * @return the {@link EventManager}
     */
    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * Get the audio manager used to send audio to the bot instance.
     *
     * @return the {@link AudioManager}
     */
    public AudioManager getAudioManager() {
        return audioManager;
    }

    /**
     * Get the active JDA
     *
     * @return the jda.
     */
    public JDA getJDA() {
        return jda;
    }

    /**
     * Get the logger.
     *
     * @return a slj4j logger.
     */
    public static Logger getLogger() {
        return LOGGER;
    }
}

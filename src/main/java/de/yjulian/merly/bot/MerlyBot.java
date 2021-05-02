package de.yjulian.merly.bot;

import de.yjulian.merly.events.JDABuildEvent;
import de.yjulian.merly.subsystem.service.Service;
import de.yjulian.merly.subsystem.service.ServiceManager;
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
import de.yjulian.merly.util.Scheduler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MerlyBot {

    private final static int SCHEDULED_CORE_SIZE = 4;
    private final static int EXECUTOR_CORE_SIZE = 8;

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
    private Scheduler scheduler;
    private ServiceManager serviceManager;

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
        this.scheduler.shutdown();  // shutting down the scheduler
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
        this.scheduler = new Scheduler(SCHEDULED_CORE_SIZE, EXECUTOR_CORE_SIZE);
        this.consoleManager = new ConsoleManager(System.in);
        this.commandManager = new CommandManagerImpl();

        this.eventManager.addEventAdapter(this.moduleManager, this.consoleManager, this.commandManager);
    }

    /**
     * Method to handle the initialization.
     */
    private void init() {
        setProgramState(ProgramState.INIT);

        this.serviceManager = new ServiceManager();
        this.audioManager = new AudioManager();
    }

    /**
     * Method to handle the post initialization.
     *
     * @throws Exception an exception.
     */
    private void postInit() throws Exception {
        setProgramState(ProgramState.POST_INIT);

        JDABuilder jdaBuilder = JDABuilder.create(token, GatewayIntent.getIntents(GatewayIntent.DEFAULT));

        JDABuildEvent jdaBuildEvent = new JDABuildEvent(jdaBuilder);    // create a new jda build event
        getEventManager().fireEvent(jdaBuildEvent); // fire the event
        jdaBuilder = jdaBuildEvent.getJDABuilder(); // get the jda builder back

        this.jda = jdaBuilder.build();

        addDefaultEventListeners();
    }

    /**
     * Initialize the default event listeners from the bot.
     */
    private void addDefaultEventListeners() {
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
     * Register a new service in the bot.
     * This is a shortcut to {@link ServiceManager#registerService(Service)}.
     *
     * @param service the service to register.
     */
    public static void registerService(Service service) {
        getInstance().getServiceManager().registerService(service);
    }

    /**
     * Get the main service manager.
     *
     * @return the service manager.
     */
    public ServiceManager getServiceManager() {
        return getInstance().serviceManager;
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

    public Scheduler getScheduler() {
        return scheduler;
    }
}

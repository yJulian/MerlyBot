package de.yjulian.merly.console;

import de.yjulian.merly.ProgramState;
import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.console.commands.ModuleCommand;
import de.yjulian.merly.events.EventAdapter;
import de.yjulian.merly.events.EventListener;
import de.yjulian.merly.events.ProgramStateChangedEvent;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.*;

public class ConsoleManager extends Thread implements EventAdapter {

    private static final String COMMAND_NOT_FOUND = "The command %s was not found.";

    private final Scanner scanner;
    private final List<ConsoleCommand> consoleCommands;


    public ConsoleManager(InputStream input) {
        super("Console-Thread");
        setDaemon(true);
        this.scanner = new Scanner(input);
        this.consoleCommands = new ArrayList<>();

        start();
    }

    public void addCommand(ConsoleCommand command, ConsoleCommand... commands) {
        this.consoleCommands.add(command);
        this.consoleCommands.addAll(Arrays.asList(commands));
    }

    public List<ConsoleCommand> getConsoleCommands() {
        return Collections.unmodifiableList(consoleCommands);
    }

    @EventListener
    public void onProgramStateChange(ProgramStateChangedEvent event) {
        if (event.getProgramState().equals(ProgramState.POST_INIT)) {
            registerDefault();
        }
    }

    private void registerDefault() {
        addCommand(new ModuleCommand());
    }

    @Override
    public void run() {
        String line;
        while ((line = scanner.nextLine()) != null) {
            String[] data = line.split(" ");

            String prefix = data[0];

            ConsoleCommand command = getCommand(prefix);

            if (command == null) {
                MerlyBot.getLogger().info(String.format(COMMAND_NOT_FOUND, prefix));
            } else {
                String[] arguments = new String[data.length - 1];
                if (data.length > 1) {
                    System.arraycopy(data, 1, arguments, 0, data.length - 1);
                }
                command.execute(arguments);
            }
        }
    }

    @Nullable
    private ConsoleCommand getCommand(String prefix) {
        return consoleCommands
                .stream()
                .filter(consoleCommand -> consoleCommand.prefix().equals(prefix))
                .findFirst()
                .orElse(null);
    }

}

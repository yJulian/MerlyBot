package de.yjulian.merly.bot;

import de.yjulian.merly.bot.commands.CommandListener;
import de.yjulian.merly.bot.commands.CommandManager;
import de.yjulian.merly.bot.events.ReadyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class MerlyBot {

    private static final Logger LOGGER = LoggerFactory.getLogger("de.yjulian.merly");

    private final JDA jda;

    public MerlyBot(String token) throws LoginException {
        this.jda = JDABuilder
                .create(token, GatewayIntent.getIntents(GatewayIntent.DEFAULT))
                .build();

        CommandManager commandManager = new CommandManager();

        this.jda.addEventListener(
                new ReadyListener(),
                new CommandListener(commandManager)
        );
    }

    public JDA getJda() {
        return jda;
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}

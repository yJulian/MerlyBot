package de.yjulian.merly.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class MerlyBot {

    private static final Logger LOGGER = LoggerFactory.getLogger("de.yjulian.merly");

    private final JDA jda;

    public MerlyBot(String token) throws LoginException {
        this.jda = new JDABuilder()
                .setToken(token)
                .build();
    }

    public JDA getJda() {
        return jda;
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}

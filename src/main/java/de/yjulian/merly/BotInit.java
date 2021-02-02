package de.yjulian.merly;

import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.util.EnvUtil;

import javax.security.auth.login.LoginException;

public class BotInit {

    public static void main(String[] args) {
        try {
            new MerlyBot(EnvUtil.getVariable("bot.token"));
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}

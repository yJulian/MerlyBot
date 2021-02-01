package de.yjulian.merly;

import de.yjulian.merly.bot.MerlyBot;

import javax.security.auth.login.LoginException;

public class BotInit {

    public static void main(String[] args) {
        try {
            new MerlyBot(System.getenv("token"));
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}

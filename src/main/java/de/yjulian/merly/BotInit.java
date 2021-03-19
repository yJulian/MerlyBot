package de.yjulian.merly;

import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.util.EnvUtil;

public class BotInit {

    public static void main(String[] args) {
        try {
            String botToken = EnvUtil.getVariable("bot.token");
            MerlyBot merlyBot = new MerlyBot(botToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

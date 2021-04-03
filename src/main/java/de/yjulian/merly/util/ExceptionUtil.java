package de.yjulian.merly.util;

import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.data.Malfunction;
import de.yjulian.merly.database.DatabaseCollection;
import de.yjulian.merly.database.Database;
import de.yjulian.merly.events.EventManager;
import de.yjulian.merly.events.MalfunctionOccurredEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class ExceptionUtil {

    public static Malfunction handleException(TextChannel textChannel, Member member, Exception exception) {
        Malfunction malfunction = new Malfunction(textChannel, member, exception);

        fireEvent(malfunction);
        Database.insertData(DatabaseCollection.MALFUNCTION, malfunction, Malfunction.class);

        return malfunction;
    }

    public static Malfunction handleException(PrivateChannel privateChannel, User user, Exception exception) {
        Malfunction malfunction = new Malfunction(privateChannel, user, exception);

        fireEvent(malfunction);
        Database.insertData(DatabaseCollection.MALFUNCTION, malfunction, Malfunction.class);

        return malfunction;
    }

    private static void fireEvent(Malfunction malfunction) {
        MalfunctionOccurredEvent moe = new MalfunctionOccurredEvent(malfunction);
        MerlyBot.getInstance().getEventManager().fireEventAsync(moe, null);
    }

}

package de.yjulian.merly.util;

import de.yjulian.merly.data.Malfunction;
import de.yjulian.merly.database.Collection;
import de.yjulian.merly.database.Database;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class ExceptionUtil {

    public static Malfunction handleException(TextChannel textChannel, Member member, Exception exception) {
        Malfunction malfunction = new Malfunction(textChannel, member, exception);

        Database.insertData(Collection.MALFUNCTION, malfunction, Malfunction.class);

        return malfunction;
    }

    public static Malfunction handleException(PrivateChannel privateChannel, User user, Exception exception) {
        Malfunction malfunction = new Malfunction(privateChannel, user, exception);

        Database.insertData(Collection.MALFUNCTION, malfunction, Malfunction.class);

        return malfunction;
    }

}

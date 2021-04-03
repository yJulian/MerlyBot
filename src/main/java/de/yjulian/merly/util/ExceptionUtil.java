package de.yjulian.merly.util;

import com.mongodb.client.model.Filters;
import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.data.Malfunction;
import de.yjulian.merly.data.codecs.MalfunctionCodec;
import de.yjulian.merly.database.DatabaseCollection;
import de.yjulian.merly.database.Database;
import de.yjulian.merly.events.EventManager;
import de.yjulian.merly.events.MalfunctionOccurredEvent;
import net.dv8tion.jda.api.entities.*;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class ExceptionUtil {

    /**
     * Log a new malfunction to the database.
     *
     * @param textChannel the text channel where the exception happened.
     * @param member the member that executed the command.
     * @param exception the exception that occurred.
     * @return a new Malfunction object.
     */
    public static Malfunction handleException(TextChannel textChannel, Member member, Exception exception) {
        Malfunction malfunction = new Malfunction(textChannel, member, exception);

        fireEvent(malfunction);
        Database.insertData(DatabaseCollection.MALFUNCTION, malfunction, Malfunction.class);

        return malfunction;
    }

    /**
     * Log a new malfunction to the database.
     *
     * @param privateChannel the private channel where the exception occurred.
     * @param user the user that caused the exception
     * @param exception the exception that occurred.
     * @return a new Malfunction object.
     */
    public static Malfunction handleException(PrivateChannel privateChannel, User user, Exception exception) {
        Malfunction malfunction = new Malfunction(privateChannel, user, exception);

        fireEvent(malfunction);
        Database.insertData(DatabaseCollection.MALFUNCTION, malfunction, Malfunction.class);

        return malfunction;
    }

    /**
     * Get a malfunction with a specific identity or null if not found.
     *
     * @param idString the identity to lookup.
     * @return a malfunction or null.
     */
    public static Malfunction getMalfunction(String idString) {
        if (!ObjectId.isValid(idString)) {
            return null;
        }
        ObjectId id = new ObjectId(idString);

        return DatabaseCollection.MALFUNCTION.getCollection()
                .find(Filters.eq(MalfunctionCodec.ID_KEY, id)).first();
    }

    /**
     * Get all malfunctions from a specific user.
     *
     * @param user the user to lookup.
     * @return a collection with all malfunctions. If no malfunction occurred the collection is empty.
     */
    public static Collection<Malfunction> getMalfunctions(User user) {
        Bson filter = Filters.eq(MalfunctionCodec.USER_KEY, user.getIdLong());
        return getMalfunctions(filter);
    }

    /**
     * Get all malfunctions that occurred on a specific guild.
     *
     * @param guild the guild to lookup.
     * @return a collection with all malfunction that occurred in the specified guild. The collection may be
     * empty if no exceptions occurred on the guild.
     */
    public static Collection<Malfunction> getMalfunctions(Guild guild) {
        Bson filter = Filters.eq(MalfunctionCodec.GUILD_KEY, guild.getIdLong());
        return getMalfunctions(filter);
    }

    /**
     * Get all malfunctions that happened in a specific message channel. Private or guild text channels are legible.
     *
     * @param messageChannel the message channel to look up.
     * @return a collection with all exceptions that occurred in the specified channel. The collection may be empty.
     */
    public static Collection<Malfunction> getMalfunctions(MessageChannel messageChannel) {
        Bson filter = Filters.eq(MalfunctionCodec.CHANNEL_KEY, messageChannel.getIdLong());
        return getMalfunctions(filter);
    }

    /**
     * Get the malfunctions directly from the database with a specified BSON filter.
     *
     * @param filter the filter to look up.
     * @return a collection with all malfunctions that match the filter. The collection may be null.
     */
    private static Collection<Malfunction> getMalfunctions(Bson filter) {
        Collection<Malfunction> malfunctions = new LinkedList<>();
        DatabaseCollection.MALFUNCTION.getCollection().find(filter).forEach(malfunctions::add);
        return malfunctions;
    }

    /**
     * Fire the internal event when a new malfunction gets logged. This event is fired async to
     * help to reduce io waits on command execution.
     *
     * @param malfunction the malfunction that happened.
     */
    private static void fireEvent(Malfunction malfunction) {
        MalfunctionOccurredEvent moe = new MalfunctionOccurredEvent(malfunction);
        MerlyBot.getInstance().getEventManager().fireEventAsync(moe, null);
    }

}

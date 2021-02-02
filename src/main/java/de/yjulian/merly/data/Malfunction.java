package de.yjulian.merly.data;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Malfunction {

    private final ObjectId _id;
    private final long channelId;
    private final long userId;
    private final long guildId;
    private final String message;
    private final List<String> stacktrace;
    private final String exception;

    public Malfunction(ObjectId _id, long channelId, long userId, long guildId, String message, List<String> stacktrace, String exception) {
        this._id = _id;
        this.channelId = channelId;
        this.userId = userId;
        this.guildId = guildId;
        this.message = message;
        this.stacktrace = stacktrace;
        this.exception = exception;
    }

    public Malfunction(TextChannel channel, Member member, Exception exception) {
        this._id = new ObjectId();

        this.channelId = channel.getIdLong();
        this.userId = member.getUser().getIdLong();
        this.guildId = member.getGuild().getIdLong();

        this.message = exception.getMessage();
        this.stacktrace = new ArrayList<>();
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            this.stacktrace.add(stackTraceElement.toString());
        }
        this.exception = exception.getClass().toString();
    }
}

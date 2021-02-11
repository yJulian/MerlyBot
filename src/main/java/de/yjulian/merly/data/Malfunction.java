package de.yjulian.merly.data;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Malfunction {

    private final ObjectId _id;
    private final long channelId;
    private final long userId;
    private final long guildId;
    private final String message;
    private final String exception;
    private final List<String> stacktrace;

    public Malfunction(ObjectId _id, long channelId, long userId, long guildId, String message, String exception, List<String> stacktrace) {
        this._id = _id;
        this.channelId = channelId;
        this.userId = userId;
        this.guildId = guildId;
        this.message = message;
        this.exception = exception;
        this.stacktrace = stacktrace;
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

    public Malfunction(PrivateChannel privateChannel, User user, Exception exception) {
        this._id = new ObjectId();

        this.channelId = privateChannel.getIdLong();
        this.userId = user.getIdLong();
        this.guildId = 0L;

        this.message = exception.getMessage();
        this.stacktrace = new ArrayList<>();
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            this.stacktrace.add(stackTraceElement.toString());
        }
        this.exception = exception.getClass().toString();
    }

    public ObjectId getId() {
        return _id;
    }

    public long getChannelId() {
        return channelId;
    }

    public long getUserId() {
        return userId;
    }

    public long getGuildId() {
        return guildId;
    }

    public String getMessage() {
        return message;
    }

    public String getException() {
        return exception;
    }

    public List<String> getStacktrace() {
        return stacktrace;
    }
}

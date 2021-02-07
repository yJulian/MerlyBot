package de.yjulian.merly.data.codecs;

import de.yjulian.merly.data.Malfunction;
import de.yjulian.merly.util.DatabaseUtil;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.List;

public class MalfunctionCodec implements Codec<Malfunction> {

    private static final String ID_KEY = "_id";
    private static final String CHANNEL_KEY = "channel";
    private static final String USER_KEY = "user";
    private static final String GUILD_KEY = "guild";
    private static final String MESSAGE_KEY = "message";
    private static final String EXCEPTION_KEY = "exception";
    private static final String STACKTRACE_KEY = "stacktrace";

    @Override
    public Malfunction decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        ObjectId id = reader.readObjectId(ID_KEY);
        long channel = reader.readInt64(CHANNEL_KEY);
        long user = reader.readInt64(USER_KEY);
        long guild = reader.readInt64(GUILD_KEY);
        String message = reader.readString(MESSAGE_KEY);
        String exception = reader.readString(EXCEPTION_KEY);
        List<String> stacktrace = DatabaseUtil.readArray(reader, decoderContext, String.class);

        reader.readEndDocument();

        return new Malfunction(id, channel, user, guild, message, exception, stacktrace);
    }

    @Override
    public void encode(BsonWriter writer, Malfunction malfunction, EncoderContext encoderContext) {
        writer.writeStartDocument();

        writer.writeObjectId(ID_KEY, malfunction.getId());
        writer.writeInt64(CHANNEL_KEY, malfunction.getChannelId());
        writer.writeInt64(USER_KEY, malfunction.getUserId());
        writer.writeInt64(GUILD_KEY, malfunction.getGuildId());
        writer.writeString(MESSAGE_KEY, malfunction.getMessage());
        writer.writeString(EXCEPTION_KEY, malfunction.getException());
        DatabaseUtil.writeArray(writer, encoderContext, STACKTRACE_KEY, malfunction.getStacktrace(), String.class);

        writer.writeEndDocument();
    }

    @Override
    public Class<Malfunction> getEncoderClass() {
        return Malfunction.class;
    }
}

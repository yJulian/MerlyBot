package de.yjulian.merly.database.codecs;

import de.yjulian.merly.data.Malfunction;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

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



        reader.readEndDocument();

        return null;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Malfunction malfunction, EncoderContext encoderContext) {

    }

    @Override
    public Class<Malfunction> getEncoderClass() {
        return Malfunction.class;
    }
}

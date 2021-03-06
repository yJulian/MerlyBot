package de.yjulian.merly.util;

import de.yjulian.merly.database.Database;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.LinkedList;
import java.util.List;

public class DatabaseUtil {

    public static <T> List<T> readArray(BsonReader reader, DecoderContext context, Class<T> aClass) {
        reader.readStartArray();

        List<T> data = new LinkedList<>();

        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            data.add(readValue(reader, context, aClass));
        }

        reader.readEndArray();

        return data;
    }

    public static <T> void writeArray(BsonWriter writer, EncoderContext context, String name, List<T> data, Class<T> aClass) {
        writer.writeStartArray(name);

        for (T entry : data) {
            writeValue(writer, context, entry, aClass);
        }

        writer.writeEndArray();
    }

    private static <T> T readValue(BsonReader reader, DecoderContext context, Class<T> aClass) {
        Codec<T> codec = Database.getInstance().getCodecRegistry().get(aClass);
        return codec.decode(reader, context);
    }

    private static <T> void writeValue(BsonWriter writer, EncoderContext context, T value, Class<T> aClass) {
        Codec<T> codec = Database.getInstance().getCodecRegistry().get(aClass);
        codec.encode(writer, value, context);
    }

}

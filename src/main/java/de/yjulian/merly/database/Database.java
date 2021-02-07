package de.yjulian.merly.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.jetbrains.annotations.NotNull;

import de.yjulian.merly.data.codecs.MalfunctionCodec;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String HOST = System.getenv("mongo.host");
    private static final int PORT = Integer.parseInt(System.getenv("mongo.port"));
    private static final String mongoPassword = System.getenv("mongo.auth.password");
    private static final String mongoAdminDb = System.getenv("mongo.auth.adminDb");
    private static final String mongoUsername = System.getenv("mongo.auth.username");
    private static final String DATABASE = System.getenv("mongo.database");
    private static Database instance;

    private final MongoClient client;
    private final MongoDatabase database;
    private final List<Codec<?>> codecs = new ArrayList<>();
    private final CodecRegistry codecRegistry;

    /**
     * Create a new database object.
     */
    public Database() {
        instance = this;

        MongoCredential credential = MongoCredential.createCredential(
                mongoUsername,
                mongoAdminDb,
                mongoPassword.toCharArray()
        );

        registerCodecs();

        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        CodecRegistry extraCodecs = CodecRegistries.fromCodecs(this.codecs);
        codecRegistry = CodecRegistries.fromRegistries(defaultCodecRegistry, extraCodecs);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(String.format("mongodb://%s:%s", HOST, PORT)))
                .credential(credential)
                .codecRegistry(this.codecRegistry)
                .applicationName("MerlyBot")
                .build();

        this.client = MongoClients.create(settings);
        this.database = this.client.getDatabase(DATABASE);
    }

    public static Database getInstance() {
        return instance;
    }

    private void registerCodecs() {
        codecs.add(new MalfunctionCodec());
    }

    @NotNull
    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    /**
     * Get a database with a specified name.
     *
     * @param name the name
     * @return a database
     */
    public MongoDatabase getDatabase(String name) {
        return this.client.getDatabase(name);
    }

    /**
     * Get the project database.
     *
     * @return the project database.
     */
    public MongoDatabase getDatabase() {
        return database;
    }

    public static MongoCollection<Document> getCollection(Collection collection) {
        return getCollection(collection, Document.class);
    }

    public static <T> MongoCollection<T> getCollection(Collection collection, Class<T> type) {
        return instance.getDatabase().getCollection(collection.getName(), type);
    }

    public static <T> void insertData(Collection collection, T data, Class<T> aClass) {
        MongoCollection<T> col = getCollection(collection, aClass);
        col.insertOne(data);
    }

}

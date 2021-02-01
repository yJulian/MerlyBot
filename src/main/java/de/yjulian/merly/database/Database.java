package de.yjulian.merly.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Database {

    private static final String HOST = System.getenv("mongo.host");
    private static final int PORT = Integer.parseInt(System.getenv("mongo.port"));
    private static final String DATABASE = System.getenv("mongo.database");

    private final MongoClient client;
    private final MongoDatabase database;

    /**
     * Create a new database object.
     */
    public Database() {
        this.client = MongoClients.create(String.format("mongodb://%s:%s", HOST, PORT));
        this.database = this.client.getDatabase(DATABASE);
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
}

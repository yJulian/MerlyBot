package de.yjulian.merly.database;


import com.mongodb.client.MongoCollection;
import de.yjulian.merly.data.Malfunction;

public enum Collection {

    MALFUNCTION("Malfunction", Malfunction.class);

    private final String name;
    private final Class<?> type;

    Collection(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    /**
     * Get the MongoCollection from the collection
     * @param aClass the class. Use the data type from the Codec (E.g. {@link Malfunction}).
     * @param <T> the type parameter
     * @return a MongoCollection
     */
    public <T> MongoCollection<T> getCollection(Class<T> aClass) {
        return Database.getCollection(this, aClass);
    }

}

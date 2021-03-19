package de.yjulian.merly.database;

import com.mongodb.client.MongoCollection;
import de.yjulian.merly.bot.MerlyBot;
import de.yjulian.merly.data.Malfunction;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class DatabaseCollection<T> {

    // This set has to be registered before the first entry
    private static final Set<DatabaseCollection<?>> DATABASE_COLLECTIONS = new HashSet<>();

    // "enum" entries after this comment
    public static final DatabaseCollection<Malfunction> MALFUNCTION
            = new DatabaseCollection<>("Malfunction", Malfunction.class);


    // no entries after this comment

    // logic to "fake" an enum
    private final String name;
    private final Class<T> type;

    private DatabaseCollection(String name, Class<T> type) {
        this.name = name;
        this.type = type;

        DATABASE_COLLECTIONS.add(this);
    }

    public static DatabaseCollection<?>[] values() {
        return DATABASE_COLLECTIONS.toArray(new DatabaseCollection[0]);
    }

    public String name() {
        try {
            for (Field field : this.getClass().getFields()) {
                if (field.get(null) == this) {
                    return field.getName();
                }
            }
        } catch (IllegalAccessException e) {
            MerlyBot.getLogger().error("Exception in Collection name", e);
        }

        throw new RuntimeException(
                "An error occurred fetching the name from the field. " +
                "This is an error related to the MerlyBot API."
        );
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    /**
     * Get the MongoCollection from the collection
     * @return a MongoCollection
     */
    public MongoCollection<T> getCollection() {
        return Database.getCollection(this, this.type);
    }

}

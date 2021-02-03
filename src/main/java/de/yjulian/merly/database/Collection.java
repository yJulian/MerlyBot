package de.yjulian.merly.database;


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
}

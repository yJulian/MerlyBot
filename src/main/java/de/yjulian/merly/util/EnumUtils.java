package de.yjulian.merly.util;

public class EnumUtils {

    /**
     * Get a enum entry with a specific key or the default value.
     * @param aEnum the enum to check.
     * @param key the key to find.
     * @param defaultValue the default value.
     * @param <T> the type
     * @return the enum constant with the key or the default value.
     */
    public static <T extends Enum<T>> T getOrDefault(Class<T> aEnum, String key, T defaultValue) {
        try {
            return Enum.valueOf(aEnum, key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

}

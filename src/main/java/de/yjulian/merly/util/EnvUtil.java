package de.yjulian.merly.util;

import de.yjulian.merly.exceptions.EnvironmentException;
import org.jetbrains.annotations.NotNull;

public class EnvUtil {

    /**
     * Get a environment key or throw an exception if there is no environment variable with the
     * specified key or the variable is empty.
     *
     * @param key the key to look for.
     * @return a String
     * @throws EnvironmentException when there is no environment variable with the specified key.
     */
    @NotNull
    public static String getVariable(String key) {
        String value = System.getenv(key);

        if (value == null) {
            throw new EnvironmentException(String.format("There is no environment variable with key %s", key));
        }

        if (value.equals("")) {
            throw new EnvironmentException(String.format("The environment variable %s is empty.", key));
        }

        return value;
    }

}

package de.yjulian.merly.util;

import de.yjulian.merly.exceptions.EnvironmentException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class EnvUtil {

    private static final File envFile = new File(".env");
    private static final Map<String, String> environment;

    // Initializer to load .env file
    static {
        environment = new HashMap<>();
        if (envFile.exists()) {
            System.out.println("Trying to load env file...");
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(envFile));
                for (String propertyName : properties.stringPropertyNames()) {
                    environment.put(propertyName, properties.getProperty(propertyName));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
        if (environment.containsKey(key)) {
            return environment.get(key);
        }

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

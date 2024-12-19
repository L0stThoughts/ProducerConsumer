package config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigManager is responsible for loading configurations from a properties file.
 * It supports loading files from both the classpath and an absolute file path.
 */
public class ConfigManager {
    private final Properties properties = new Properties();

    /**
     * Constructor to initialize ConfigManager with a configuration file path.
     *
     * @param filePath the path to the configuration file.
     *                 If the file is in the classpath, provide only the file name.
     */
    public ConfigManager(String filePath) {
        try (InputStream is = loadFile(filePath)) {
            if (is == null) {
                throw new RuntimeException("Configuration file not found: " + filePath);
            }
            properties.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration file: " + filePath, e);
        }
    }

    /**
     * Loads a configuration file.
     *
     * @param filePath the path to the file.
     * @return an InputStream of the file content.
     * @throws Exception if the file cannot be found or opened.
     */
    private InputStream loadFile(String filePath) throws Exception {
        // Try to load from the classpath
        InputStream classpathStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (classpathStream != null) {
            return classpathStream;
        }

        // Fallback to loading from a file path
        return new FileInputStream(filePath);
    }

    /**
     * Retrieves a configuration value as a String.
     *
     * @param key          the key to look up.
     * @param defaultValue the default value to return if the key is not found.
     * @return the value associated with the key, or the defaultValue.
     */
    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Retrieves a configuration value as an integer.
     *
     * @param key          the key to look up.
     * @param defaultValue the default value to return if the key is not found or is invalid.
     * @return the value associated with the key as an integer, or the defaultValue.
     */
    public int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.err.println("Invalid integer value for key: " + key + ". Using default: " + defaultValue);
            }
        }
        return defaultValue;
    }

    /**
     * Retrieves a configuration value as a boolean.
     *
     * @param key          the key to look up.
     * @param defaultValue the default value to return if the key is not found or is invalid.
     * @return the value associated with the key as a boolean, or the defaultValue.
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }

    /**
     * Checks if a specific key exists in the configuration file.
     *
     * @param key the key to check for.
     * @return true if the key exists, false otherwise.
     */
    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }
}

package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader - Reads config.properties at runtime
 * Supports environment-specific property files (dev, staging, prod)
 */
public class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;

    static {
        String env = System.getProperty("env", "staging");
        String configFile = "config/" + env + ".properties";
        log.info("Loading config from: {}", configFile);

        properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream(configFile)) {
            if (input == null) {
                // fallback to default
                InputStream fallback = ConfigReader.class.getClassLoader()
                        .getResourceAsStream("config/staging.properties");
                properties.load(fallback);
            } else {
                properties.load(input);
            }
        } catch (IOException e) {
            log.error("Failed to load config: {}", e.getMessage());
            throw new RuntimeException("Could not load config: " + configFile);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            log.warn("Property '{}' not found in config.", key);
        }
        return value;
    }
}

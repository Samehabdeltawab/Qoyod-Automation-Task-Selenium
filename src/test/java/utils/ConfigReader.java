package utils;

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class to read configuration properties from the config.properties
 * file.
 * This ensures that environment-specific data is decoupled from the test logic.
 */
public class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private Properties prop;
    private static final String CONFIG_FILE = "src/test/resources/config.properties";

    /**
     * Constructor initializes the Properties object and loads the configuration
     * file.
     * Uses a relative path to ensure portability across different environments.
     */
    public ConfigReader() {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE);
            prop = new Properties();
            prop.load(fis);
            fis.close();
            logger.info("Configuration properties loaded successfully from: " + CONFIG_FILE);
        } catch (Exception e) {
            logger.error("Failed to load configuration file at: " + CONFIG_FILE, e);
            throw new RuntimeException("Critical Error: Could not load config.properties. Please verify the file path.",
                    e);
        }
    }

    /**
     * Retrieves the base URL from the properties file.
     * 
     * @return String containing the base URL.
     * @throws RuntimeException if the property is missing or empty.
     */
    public String getBaseUrl() {
        String baseUrl = prop.getProperty("baseUrl");
        if (baseUrl == null || baseUrl.isEmpty()) {
            logger.error("The property 'baseUrl' is missing or empty in config.properties");
            throw new RuntimeException("Property 'baseUrl' not found in configuration.");
        }
        return baseUrl;
    }

    /**
     * Retrieves the browser name (e.g., chrome, firefox) from the properties file.
     * 
     * @return String containing the browser type.
     * @throws RuntimeException if the property is missing or empty.
     */
    public String getBrowser() {
        String browser = prop.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            logger.error("The property 'browser' is missing or empty in config.properties");
            throw new RuntimeException("Property 'browser' not found in configuration.");
        }
        return browser;
    }

    /**
     * Generic method to retrieve any property by its key name.
     * Useful for custom properties added in the future.
     * 
     * @param key The property key name.
     * @return The value associated with the key.
     */
    public String getProperty(String key) {
        return prop.getProperty(key);
    }
}
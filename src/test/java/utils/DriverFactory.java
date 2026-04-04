package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver initDriver() {
        try {
            ConfigReader config = new ConfigReader();
            String browser = config.getBrowser();

            if (browser == null || browser.isEmpty()) {
                logger.error("Browser configuration not found!");
                throw new RuntimeException("Browser configuration not found in config.properties");
            }

            if (browser.equalsIgnoreCase("chrome")) {
                driver.set(new ChromeDriver());
                logger.info("Chrome browser initialized successfully");
            } else {
                logger.error("Unsupported browser: " + browser);
                throw new RuntimeException("Unsupported browser: " + browser);
            }

            driver.get().manage().window().maximize();
            logger.info("Browser window maximized");
            return driver.get();

        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver: " + e.getMessage(), e);
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }

    public static WebDriver getDriver() {
        /**
         * NOTE: This method is currently NOT USED in the project.
         * It was added for future use cases where we might need to retrieve
         * the existing WebDriver instance without reinitializing it.
         * 
         * Use Case Example: If we need to access the driver from a utility class
         * or perform additional setup without creating a new browser instance.
         * 
         * Can be removed if not needed in future development.
         */
        WebDriver driverInstance = driver.get();
        if (driverInstance == null) {
            logger.warn("WebDriver is null. Reinitializing...");
            return initDriver();
        }
        return driverInstance;
    }

    public static void quitDriver() {
        try {
            if (driver.get() != null) {
                driver.get().quit();
                logger.info("Browser quit successfully");
            }
        } catch (Exception e) {
            logger.error("Error while quitting driver: " + e.getMessage(), e);
        } finally {
            driver.remove();
        }
    }
}
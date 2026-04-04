package base;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utils.ConfigReader;
import utils.DriverFactory;
import java.io.File;

public class BaseTest {

    protected WebDriver driver;
    protected ConfigReader config;

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    private static final String ALLURE_RESULTS_PATH = "target/allure-results";

    /**
     * Pre-Suite cleanup to ensure Allure reports only contain results
     * from the current execution, preventing data accumulation.
     */
    @BeforeSuite
    public void cleanAllureResults() {
        File folder = new File(ALLURE_RESULTS_PATH);
        if (folder.exists()) {
            deleteDirectory(folder);
            logger.info("Existing Allure results cleared.");
        }
        folder.mkdirs();
    }

    /**
     * Recursive method to delete directories and their contents.
     */
    private void deleteDirectory(File dir) {
        File[] allContents = dir.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                if (file.isDirectory())
                    deleteDirectory(file);
                else
                    file.delete();
            }
        }
        dir.delete();
    }

    /**
     * Initializes the WebDriver and navigates to the local HTML test file
     * using a generic relative path.
     */
    @BeforeMethod
    public void setup() {
        // 1. Initialize WebDriver via Factory
        driver = DriverFactory.initDriver();
        logger.info("WebDriver initialized successfully.");

        // 2. Load configurations
        config = new ConfigReader();

        // 3. Construct a Generic Path to the local HTML file
        // System.getProperty("user.dir") fetches the current project root dynamically
        String projectPath = System.getProperty("user.dir");

        // Combine project path with relative resource path using 'file:///' protocol
        String htmlFilePath = "file:///" + projectPath + "/src/test/resources/login.html";

        // Ensure path compatibility by replacing backslashes with forward slashes
        htmlFilePath = htmlFilePath.replace("\\", "/");

        // 4. Navigate to the constructed URL
        driver.get(htmlFilePath);
        logger.info("Navigated to generic path: " + htmlFilePath);
    }

    /**
     * Handles post-test actions. If a test fails, a screenshot is
     * automatically attached to the Allure report.
     */
    @AfterMethod
    public void teardown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            saveScreenshotToAllure();
            logger.error("Test failed: attaching screenshot to report.");
        }
        DriverFactory.quitDriver();
        logger.info("WebDriver session closed.");
    }

    /**
     * Allure Attachment for capturing screenshots.
     * This method is triggered automatically on failure via the teardown.
     */
    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] saveScreenshotToAllure() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
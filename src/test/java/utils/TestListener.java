package utils;

import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Custom Listener to decouple Allure reporting from Test Classes.
 * It dynamically updates Allure test names using parameters from DataProviders.
 */
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Object[] parameters = result.getParameters();
        
        // Check if DataProvider is passing TestID and Description (first two params)
        if (parameters != null && parameters.length >= 2) {
            String testId = parameters[0].toString();
            String description = parameters[1].toString();
            
            // Inject names into Allure lifecycle without hardcoding them in @Test files
            Allure.getLifecycle().updateTestCase(testCase -> {
                testCase.setName(testId + ": " + description);
            });
        }
    }
}
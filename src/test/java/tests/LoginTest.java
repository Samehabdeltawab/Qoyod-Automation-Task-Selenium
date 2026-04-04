package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import io.qameta.allure.*;

/**
 * Login Test Class - Final Professional Version
 * Optimized for Allure Reporting (Epic → Feature → Story)
 */
@Epic("User Authentication")
@Feature("Login Functionality")
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    /**
     * Reads test data from JSON file
     */
    private JSONObject getTestData(String tcId) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(
                new FileReader("src/test/resources/testCases.json"));
        return (JSONObject) jsonObject.get(tcId);
    }

    /**
     * Reusable login steps
     */
    @Step("Execute login for Test Case: {0}")
    private JSONObject performLoginSteps(String tcId) throws Exception {
        loginPage = new LoginPage(driver);
        JSONObject data = getTestData(tcId);

        loginPage.enterUsername((String) data.get("username"));
        loginPage.enterPassword((String) data.get("password"));
        loginPage.clickLogin();

        return data;
    }

    // ========================= TESTS ========================= //

    @Test(priority = 1, description = "Valid login")
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    public void testSuccessfulLogin() throws Exception {
        JSONObject data = performLoginSteps("TC-001");

        Assert.assertTrue(
                loginPage.getSuccessMessage().contains((String) data.get("expectedMessage")));
    }

    @Test(priority = 2, description = "Invalid username")
    @Story("Invalid Login - Username")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidUsername() throws Exception {
        JSONObject data = performLoginSteps("TC-002");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                (String) data.get("expectedMessage"));
    }

    @Test(priority = 3, description = "Incorrect password")
    @Story("Invalid Login - Password")
    @Severity(SeverityLevel.CRITICAL)
    public void testIncorrectPassword() throws Exception {
        JSONObject data = performLoginSteps("TC-003");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                (String) data.get("expectedMessage"));
    }

    @Test(priority = 4, description = "Empty fields validation")
    @Story("Validation Errors")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyFields() throws Exception {
        JSONObject data = performLoginSteps("TC-004");

        Assert.assertEquals(
                loginPage.getUsernameError(),
                (String) data.get("userError"));

        Assert.assertEquals(
                loginPage.getPasswordError(),
                (String) data.get("passError"));
    }
}
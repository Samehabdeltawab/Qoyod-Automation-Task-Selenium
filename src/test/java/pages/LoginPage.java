package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    private By username = By.id("username-input");
    private By password = By.id("password-input");
    private By loginBtn = By.xpath("//span[@id='sign-in-label']/parent::button");
    private By successScreen = By.id("success-screen");
    private By successMessage = By.id("success-heading");
    private By loggedInUsername = By.id("logged-in-username");
    private By generalError = By.id("error-message");
    private By usernameError = By.id("username-error");
    private By passwordError = By.id("password-error");

    // Page actions
    public void openPage(String url) {
        driver.get(url);
    }

    public void enterUsername(String user) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(username)).sendKeys(user);
    }

    public void enterPassword(String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(pass);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }

    // Get the success message after login
    public String getSuccessMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successScreen));
            WebElement msgElement = wait.until(driver -> {
                WebElement el = driver.findElement(successMessage);
                return (el.isDisplayed() && !el.getText().trim().isEmpty()) ? el : null;
            });
            return msgElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Get the logged-in username
    public String getLoggedInUsername() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successScreen));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInUsername)).getText();
        } catch (Exception e) {
            return "Success Screen Not Found";
        }
    }

    // Error messages
    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(generalError)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getUsernameError() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameError)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getPasswordError() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError)).getText();
        } catch (Exception e) {
            return "";
        }
    }
}
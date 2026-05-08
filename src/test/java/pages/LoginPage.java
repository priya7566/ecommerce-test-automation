package pages;

import org.openqa.selenium.By;

/**
 * LoginPage - Page Object for SauceDemo Login
 */
public class LoginPage extends BasePage {

    private final By usernameField  = By.id("user-name");
    private final By passwordField  = By.id("password");
    private final By loginButton    = By.id("login-button");
    private final By errorMessage   = By.cssSelector("[data-test='error']");

    public LoginPage navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
        return this;
    }

    public void login(String username, String password) {
        log.info("Logging in as: {}", username);
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}

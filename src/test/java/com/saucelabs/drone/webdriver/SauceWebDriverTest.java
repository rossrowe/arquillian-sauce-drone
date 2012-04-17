package com.saucelabs.drone.webdriver;

import com.saucelabs.drone.AbstractCase;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * @author Ross Rowe
 */
@RunWith(Arquillian.class)
public class SauceWebDriverTest extends AbstractCase {

    protected static final String USERNAME = "demo";
    protected static final String PASSWORD = "demo";

    protected static final By LOGGED_IN = By.xpath("//li[contains(text(),'Welcome')]");
    protected static final By LOGGED_OUT = By.xpath("//li[contains(text(),'Goodbye')]");

    protected static final By USERNAME_FIELD = By.id("loginForm:username");
    protected static final By PASSWORD_FIELD = By.id("loginForm:password");

    protected static final By LOGIN_BUTTON = By.id("loginForm:login");
    protected static final By LOGOUT_BUTTON = By.id("loginForm:logout");

    @Drone
    WebDriver driver;

    @Test
    public void testLoginAndLogout() {
        Assert.assertNotNull("Path is not null", contextPath);
        Assert.assertNotNull("WebDriver is not null", driver());

        driver().get(contextPath + "home.jsf");

        driver().findElement(USERNAME_FIELD).sendKeys(USERNAME);
        driver().findElement(PASSWORD_FIELD).sendKeys(PASSWORD);
        driver().findElement(LOGIN_BUTTON).click();
        checkElementPresence(driver(), LOGGED_IN, "User should be logged in!");

        driver().findElement(LOGOUT_BUTTON).click();
        checkElementPresence(driver(), LOGGED_OUT, "User should not be logged in!");

    }

    private WebDriver driver() {
        return driver;
    }

    // check is element is presence on page, fails otherwise
    protected void checkElementPresence(WebDriver driver, By by, String errorMsg) {
        try {
            Assert.assertTrue(errorMsg, driver.findElement(by) != null);
        } catch (NoSuchElementException e) {
            Assert.fail(errorMsg);
        }

    }
}

package com.saucelabs.drone;

/**
 * @author Ross Rowe
 */

import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

/**
 * Runs Arquillian Drone extension tests against a simple page.
 *
 * Uses standard settings of Selenium 2.0, that is HtmlUnitDriver by default, but allows user to pass another driver specified
 * as a System property or in the Arquillian configuration.
 *
 * @author <a href="mailto:kpiwko@redhat.com">Karel Piwko</a>
 *
 * @see org.jboss.arquillian.drone.webdriver.factory.WebDriverFactory
 */
public abstract class AbstractWebDriver {

    protected static final String USERNAME = "demo";
    protected static final String PASSWORD = "demo";

    @Test
    @InSequence(1)
    public void login() {
        LoginPage page = new LoginPage(driver());
        page.login(USERNAME, PASSWORD);
    }

    @Test
    @InSequence(2)
    public void logout() {
        LoginPage page = new LoginPage(driver());
        page.logout();
    }

    protected abstract WebDriver driver();

}
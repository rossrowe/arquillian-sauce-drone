package com.saucelabs.drone.webdriver;

import com.saucelabs.drone.AbstractWebDriver;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

/**
 *
 * Requires Sauce Connect to be running
 *
 * @author Ross Rowe
 */
@RunWith(Arquillian.class)
public class SauceWebDriverTest extends AbstractWebDriver {

    @Drone
    WebDriver driver;

    /*
    * (non-Javadoc)
    *
    * @see org.jboss.arquillian.drone.webdriver.example.AbstractWebDriverTestCase#driver()
    */
    @Override
    protected WebDriver driver() {
        return driver;
    }
}

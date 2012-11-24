package com.saucelabs.drone.webdriver;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

/**
 * @author Ross Rowe
 */
@RunWith(Arquillian.class)
public class AmazonTest {

    @Drone
    WebDriver driver;

    @Test
    public void amazonTitle() {
        driver.get("http://www.amazon.com/");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", driver.getTitle());
    }
}

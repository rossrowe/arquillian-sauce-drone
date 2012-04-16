package com.saucelabs.drone.webdriver;

import com.saucelabs.drone.AbstractSauceFactory;
import com.saucelabs.sauce_ondemand.driver.SauceOnDemandSPIImpl;
import com.saucelabs.selenium.client.factory.SeleniumFactory;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.drone.spi.Configurator;
import org.jboss.arquillian.drone.spi.Destructor;
import org.jboss.arquillian.drone.spi.Instantiator;
import org.openqa.selenium.WebDriver;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;

/**
 * @author Ross Rowe
 */
public class SauceWebDriverFactory extends AbstractSauceFactory implements Configurator<WebDriver, WebDriverConfiguration>,
        Instantiator<WebDriver,WebDriverConfiguration>, Destructor<WebDriver> {
    public WebDriverConfiguration createConfiguration(ArquillianDescriptor descriptor, Class<? extends Annotation> qualifier) {
        return new WebDriverConfiguration().configure(descriptor, qualifier);
    }

    public int getPrecedence() {
        return 0;
    }

    public void destroyInstance(WebDriver instance) {
        instance.quit();
    }

    public WebDriver createInstance(WebDriverConfiguration configuration) {
        String driver = readPropertyOrEnv("SELENIUM_DRIVER");
        if (driver == null) {
            driver = MessageFormat.format(
                    FACTORY_URL,
                    configuration.getUserName(),
                    configuration.getAccessKey(),
                    configuration.getTimeout(),
                    configuration.getOperatingSystem(),
                    configuration.getBrowser(),
                    configuration.getVersion());
            //set property for host and port
            System.setProperty("SELENIUM_HOST", configuration.getServerHost());
            System.setProperty("SELENIUM_PORT", String.valueOf(configuration.getServerPort()));
        }
        SeleniumFactory factory = new SeleniumFactory().setUri(driver);
        return new SauceOnDemandSPIImpl().createWebDriver(factory, configuration.getUrl());
    }
}

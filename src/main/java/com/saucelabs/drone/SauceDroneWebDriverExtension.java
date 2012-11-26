package com.saucelabs.drone;

import com.saucelabs.drone.selenium.SauceSeleniumFactory;
import com.saucelabs.drone.webdriver.SauceWebDriverFactory;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.drone.selenium.factory.DefaultSeleniumFactory;
import org.jboss.arquillian.drone.spi.Configurator;
import org.jboss.arquillian.drone.spi.Destructor;
import org.jboss.arquillian.drone.spi.Instantiator;
import org.jboss.arquillian.drone.webdriver.factory.WebDriverFactory;

import java.util.logging.Logger;

/**
 * @author Ross Rowe
 */
public class SauceDroneWebDriverExtension implements LoadableExtension {

    private static final Logger log = Logger.getLogger(SauceDroneWebDriverExtension.class.getName());

    public void register(ExtensionBuilder builder) {

        builder.service(Configurator.class, SauceWebDriverFactory.class);
        builder.service(Instantiator.class, SauceWebDriverFactory.class);
        builder.service(Destructor.class, SauceWebDriverFactory.class);

        builder.service(Configurator.class, SauceSeleniumFactory.class);
        builder.service(Instantiator.class, SauceSeleniumFactory.class);
        builder.service(Destructor.class, SauceSeleniumFactory.class);

        builder.override(Configurator.class, WebDriverFactory.class, SauceWebDriverFactory.class);
        builder.override(Instantiator.class, WebDriverFactory.class, SauceWebDriverFactory.class);
        builder.override(Destructor.class, WebDriverFactory.class, SauceWebDriverFactory.class);

        builder.override(Configurator.class, DefaultSeleniumFactory.class, SauceSeleniumFactory.class);
        builder.override(Instantiator.class, DefaultSeleniumFactory.class, SauceSeleniumFactory.class);
        builder.override(Destructor.class, DefaultSeleniumFactory.class, SauceSeleniumFactory.class);
    }
}

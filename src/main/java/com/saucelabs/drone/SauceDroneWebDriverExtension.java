package com.saucelabs.drone;

import com.saucelabs.drone.selenium.SauceSeleniumFactory;
import com.saucelabs.drone.webdriver.SauceWebDriverFactory;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.drone.spi.Configurator;
import org.jboss.arquillian.drone.spi.Destructor;
import org.jboss.arquillian.drone.spi.Instantiator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ross Rowe
 */
public class SauceDroneWebDriverExtension implements LoadableExtension {

    private static final Logger log = Logger.getLogger(SauceDroneWebDriverExtension.class.getName());

    public void register(ExtensionBuilder builder) {

        log.log(Level.INFO, "Registering Sauce Drone");
        System.out.println("Registering Sauce Drone");

        builder.service(Configurator.class, SauceWebDriverFactory.class);
        builder.service(Instantiator.class, SauceWebDriverFactory.class);
        builder.service(Destructor.class, SauceWebDriverFactory.class);

        builder.service(Configurator.class, SauceSeleniumFactory.class);
        builder.service(Instantiator.class, SauceSeleniumFactory.class);
        builder.service(Destructor.class, SauceSeleniumFactory.class);
    }
}

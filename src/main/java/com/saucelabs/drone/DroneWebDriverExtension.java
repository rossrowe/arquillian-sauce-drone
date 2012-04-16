package com.saucelabs.drone;

import com.saucelabs.drone.selenium.SauceSeleniumFactory;
import com.saucelabs.drone.webdriver.SauceWebDriverFactory;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.drone.spi.Configurator;
import org.jboss.arquillian.drone.spi.Destructor;
import org.jboss.arquillian.drone.spi.Instantiator;

/**
 * @author Ross Rowe
 */
public class DroneWebDriverExtension implements LoadableExtension {
    public void register(ExtensionBuilder builder) {
        builder.service(Configurator.class, SauceWebDriverFactory.class);
        builder.service(Instantiator.class, SauceWebDriverFactory.class);
        builder.service(Destructor.class, SauceWebDriverFactory.class);

        builder.service(Configurator.class, SauceSeleniumFactory.class);
        builder.service(Instantiator.class, SauceSeleniumFactory.class);
        builder.service(Destructor.class, SauceSeleniumFactory.class);
    }
}

package com.saucelabs.drone.selenium;

import com.saucelabs.drone.AbstractSauceFactory;
import com.saucelabs.sauce_ondemand.driver.SauceOnDemandSPIImpl;
import com.saucelabs.selenium.client.factory.SeleniumFactory;
import com.thoughtworks.selenium.Selenium;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.drone.spi.Configurator;
import org.jboss.arquillian.drone.spi.Destructor;
import org.jboss.arquillian.drone.spi.Instantiator;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;

/**
 * @author Ross Rowe
 */
public class SauceSeleniumFactory extends AbstractSauceFactory implements Configurator<Selenium, SauceSeleniumConfiguration>,
        Instantiator<Selenium, SauceSeleniumConfiguration>, Destructor<Selenium> {

    public SauceSeleniumConfiguration createConfiguration(ArquillianDescriptor descriptor, Class<? extends Annotation> qualifier) {
        return new SauceSeleniumConfiguration().configure(descriptor, qualifier);
    }

    public int getPrecedence() {
        return 0;
    }

    public void destroyInstance(Selenium instance) {
        instance.close();
        instance.stop();
    }

    public Selenium createInstance(SauceSeleniumConfiguration configuration) {
        //use selenium-client-factory to create DefaultSelenium instance
        //if url defined in environment variable/system property, then use that
        //if not, construct url
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
        return new SauceOnDemandSPIImpl().createSelenium(factory, configuration.getUrl());
    }


}

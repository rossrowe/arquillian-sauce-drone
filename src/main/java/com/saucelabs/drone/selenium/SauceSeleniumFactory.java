package com.saucelabs.drone.selenium;

import com.saucelabs.drone.AbstractSauceFactory;
import com.saucelabs.drone.SauceConfiguration;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.drone.spi.Configurator;
import org.jboss.arquillian.drone.spi.Destructor;
import org.jboss.arquillian.drone.spi.Instantiator;

import java.lang.annotation.Annotation;

/**
 * @author Ross Rowe
 */
public class SauceSeleniumFactory extends AbstractSauceFactory implements Configurator<Selenium, SauceConfiguration>,
        Instantiator<Selenium, SauceConfiguration>, Destructor<Selenium> {

    public SauceConfiguration createConfiguration(ArquillianDescriptor descriptor, Class<? extends Annotation> qualifier) {
        return new SauceConfiguration(SauceConfiguration.ConfigurationType.SELENIUM).configure(descriptor, qualifier);
    }

    public int getPrecedence() {
        return 0;
    }

    public void destroyInstance(Selenium instance) {
        instance.close();
        instance.stop();
    }

    /**
     * @param configuration
     * @return
     */
    public Selenium createInstance(SauceConfiguration configuration) {

        String username = readPropertyOrEnv("SAUCE_USER_NAME", configuration.getUserName());
        String apiKey = readPropertyOrEnv("SAUCE_API_KEY", configuration.getAccessKey());
        String host = readPropertyOrEnv("SELENIUM_HOST", configuration.getServerHost());
        String port = readPropertyOrEnv("SELENIUM_HOST", configuration.getServerHost());
        String browser = readPropertyOrEnv("SELENIUM_BROWSER", configuration.getBrowser());
        String version = readPropertyOrEnv("SELENIUM_VERSION", configuration.getVersion());
        String os = readPropertyOrEnv("SELENIUM_PLATFORM", configuration.getOs());
        String startingUrl = readPropertyOrEnv("SELENIUM_STARTING_URL", configuration.getUrl());
        DefaultSelenium selenium = new DefaultSelenium(
                host,
                Integer.valueOf(port),
                "{\"username\": \"" + username + "\"," +
                        "\"access-key\": \"" + apiKey + "\"," +
                        "\"os\": \"" + os + "\"," +
                        "\"browser\": \"" + browser + "\"," +
                        "\"browser-version\": \"" + version + "\"}",
                startingUrl);
        return selenium;
    }


}

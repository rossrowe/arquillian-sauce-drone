package com.saucelabs.drone.webdriver;

import com.saucelabs.drone.AbstractSauceFactory;
import com.saucelabs.drone.SauceConfiguration;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.drone.spi.Configurator;
import org.jboss.arquillian.drone.spi.Destructor;
import org.jboss.arquillian.drone.spi.Instantiator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

/**
 * @author Ross Rowe
 */
public class SauceWebDriverFactory extends AbstractSauceFactory
        implements Configurator<WebDriver, SauceConfiguration>,
        Instantiator<WebDriver,SauceConfiguration>,
        Destructor<WebDriver> {

    public SauceConfiguration createConfiguration(ArquillianDescriptor descriptor, Class<? extends Annotation> qualifier) {
        return new SauceConfiguration(SauceConfiguration.ConfigurationType.WEBDRIVER).configure(descriptor, qualifier);
    }

    public int getPrecedence() {
        return 0;
    }

    public void destroyInstance(WebDriver instance) {
        instance.quit();
    }

    /**
     * Create a RemoteWebDriver instance using the properties/environment variables set by the Sauce CI plugins.
     *
     * If the properties aren't set, then attempt to read from the {@link SauceConfiguration}.
     *
     * @param configuration
     * @return
     */
    public WebDriver createInstance(SauceConfiguration configuration) {
        String username = readPropertyOrEnv("SAUCE_USER_NAME", configuration.getUserName());
        String apiKey = readPropertyOrEnv("SAUCE_API_KEY", configuration.getAccessKey());
        String host = readPropertyOrEnv("SELENIUM_HOST", configuration.getServerHost());
        String port = readPropertyOrEnv("SELENIUM_PORT", configuration.getServerPort());
        String url = MessageFormat.format(URL_FORMAT, username, apiKey, host, port);
        String browser = readPropertyOrEnv("SELENIUM_BROWSER", configuration.getBrowser());
        String version = readPropertyOrEnv("SELENIUM_VERSION", configuration.getVersion());
        String os = readPropertyOrEnv("SELENIUM_PLATFORM", configuration.getOs());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        //TODO handle values not set
        try {
            return new RemoteWebDriver(new URL(url), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

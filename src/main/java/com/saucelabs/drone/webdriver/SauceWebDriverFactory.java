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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ross Rowe
 */
public class SauceWebDriverFactory extends AbstractSauceFactory
        implements Configurator<WebDriver, SauceConfiguration>,
        Instantiator<WebDriver, SauceConfiguration>,
        Destructor<WebDriver> {

    private static final Logger log = Logger.getLogger(SauceWebDriverFactory.class.getName());

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
     * <p/>
     * If the properties aren't set, then attempt to read from the {@link SauceConfiguration}.
     *
     * @param configuration
     * @return
     */
    public WebDriver createInstance(SauceConfiguration configuration) {
        String username = readPropertyOrEnv("SAUCE_USER_NAME", configuration.getUserName());
        if (isBlank(username)) {
            log.log(Level.WARNING, "Unable to find value for username");
        }
        String apiKey = readPropertyOrEnv("SAUCE_API_KEY", configuration.getAccessKey());
        if (isBlank(username)) {
            log.log(Level.WARNING, "Unable to find value for api key");
        }
        String host = readPropertyOrEnv("SELENIUM_HOST", configuration.getServerHost());
        if (isBlank(username)) {
            log.log(Level.WARNING, "Unable to find value for selenium host");
        }
        String port = readPropertyOrEnv("SELENIUM_PORT", configuration.getServerPort());
        if (isBlank(username)) {
            log.log(Level.WARNING, "Unable to find value for selenium port");
        }
        String url = MessageFormat.format(URL_FORMAT, username, apiKey, host, port);
        String browser = readPropertyOrEnv("SELENIUM_BROWSER", configuration.getBrowser());
        if (isBlank(username)) {
            log.log(Level.WARNING, "Unable to find value for browser");
        }
        String version = readPropertyOrEnv("SELENIUM_VERSION", configuration.getVersion());
        if (isBlank(username)) {
            log.log(Level.WARNING, "Unable to find value for version");
        }
        String os = readPropertyOrEnv("SELENIUM_PLATFORM", configuration.getOs());
        if (isBlank(username)) {
            log.log(Level.WARNING, "Unable to find value for operating system");
        }
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        try {
            log.log(Level.INFO, "Connecting to " + url);
            return new RemoteWebDriver(new URL(url), capabilities);
        } catch (MalformedURLException e) {
            log.log(Level.WARNING, "Error creating RemoteWebDriver", e);
        }
        return null;
    }

    private boolean isBlank(String value) {
        return value == null || value.equals("");
    }
}

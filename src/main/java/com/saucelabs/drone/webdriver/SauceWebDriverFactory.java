package com.saucelabs.drone.webdriver;

import com.saucelabs.drone.AbstractSauceFactory;
import com.saucelabs.drone.SauceConfiguration;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.drone.spi.Configurator;
import org.jboss.arquillian.drone.spi.Destructor;
import org.jboss.arquillian.drone.spi.DroneRegistry;
import org.jboss.arquillian.drone.spi.Instantiator;
import org.jboss.arquillian.drone.webdriver.configuration.TypedWebDriverConfiguration;
import org.jboss.arquillian.drone.webdriver.configuration.WebDriverConfiguration;
import org.jboss.arquillian.drone.webdriver.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles creating a {@link RemoteWebDriver} instance to be used to run tests with <a href="http://saucelabs.com">Sauce OnDemand</a>.
 *
 * @author Ross Rowe
 */
public class SauceWebDriverFactory extends AbstractSauceFactory
        implements Configurator<WebDriver, SauceConfiguration>,
        Instantiator<WebDriver, SauceConfiguration>,
        Destructor<WebDriver> {

    @Inject
    private Instance<DroneRegistry> registryInstance;

    private static final Logger log = Logger.getLogger(SauceWebDriverFactory.class.getName());

    /**
     * Creates a new {@link SauceConfiguration} instance using the data supplied in the {@link ArquillianDescriptor}.  We create and store
     * a {@link TypedWebDriverConfiguration} instance to cater for instances where the user specifies a non-sauce browser in the 'browserCapabilities'.
     *
     * @param descriptor
     * @param qualifier
     * @return
     */
    public SauceConfiguration createConfiguration(ArquillianDescriptor descriptor, Class<? extends Annotation> qualifier) {
        log.log(Level.INFO, "Configuring SauceWebDriverFactory");

        SauceConfiguration sauceConfiguration = new SauceConfiguration(SauceConfiguration.ConfigurationType.WEBDRIVER);
        TypedWebDriverConfiguration webDriverConfiguration = new TypedWebDriverConfiguration<WebDriverConfiguration>(WebDriverConfiguration.class).configure(descriptor,
                qualifier);
        sauceConfiguration.setWebDriverConfiguration(webDriverConfiguration);
        return sauceConfiguration.configure(descriptor, qualifier);
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
        String browserCapabilities = configuration.getWebDriverConfiguration().getBrowserCapabilities();
        if (browserCapabilities != null && !browserCapabilities.equals("") && !browserCapabilities.equals("sauce")) {
            //user has specified an override, invoke
            //WebDriverFactory won't be registered with the DroneRegistry (as the Sauce plugin overrides
            //the WebDriverFactory registration), so we directly instantiate it and populate the registryInstance
            //variable
            WebDriverFactory webDriverFactory = new WebDriverFactory();
            try {
                Field registryInstanceField = webDriverFactory.getClass().getDeclaredField("registryInstance");
                registryInstanceField.setAccessible(true);
                registryInstanceField.set(webDriverFactory, registryInstance);
                return webDriverFactory.createInstance(configuration.getWebDriverConfiguration());
            } catch (NoSuchFieldException e) {
                log.log(Level.WARNING, "Unable to instantiate WebDriver", e);
            } catch (IllegalAccessException e) {
                log.log(Level.WARNING, "Unable to instantiate WebDriver", e);
            }
        }

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

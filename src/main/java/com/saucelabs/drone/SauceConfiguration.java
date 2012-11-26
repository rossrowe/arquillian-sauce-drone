package com.saucelabs.drone;

import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.drone.configuration.ConfigurationMapper;
import org.jboss.arquillian.drone.selenium.configuration.SeleniumConfiguration;
import org.jboss.arquillian.drone.spi.DroneConfiguration;
import org.jboss.arquillian.drone.webdriver.configuration.TypedWebDriverConfiguration;

import java.lang.annotation.Annotation;

/**
 * @author Ross Rowe
 */
public class SauceConfiguration implements DroneConfiguration<SauceConfiguration> {

    public enum ConfigurationType {
        WEBDRIVER("sauce-webdriver"), SELENIUM("sauce-selenium");
        private String type;

        ConfigurationType(String type) {
            this.type = type;
        }
    }

    private TypedWebDriverConfiguration webDriverConfiguration;
    private SeleniumConfiguration seleniumConfiguration;


    private String serverPort = "80";

    protected String userName;
    protected String accessKey;
    protected String browser;
    protected String os;
    protected String version;
    private String url = "http://localhost:8080";
    private String serverHost = "ondemand.saucelabs.com";
    private int timeout = 60000;
    private int speed = 0;
    private ConfigurationType configurationType;


    public SauceConfiguration(ConfigurationType configurationType) {
        this.configurationType = configurationType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getConfigurationName() {
        return configurationType.type;
    }

    public SauceConfiguration configure(ArquillianDescriptor descriptor, Class<? extends Annotation> qualifier) {
        ConfigurationMapper.fromArquillianDescriptor(descriptor, this, qualifier);
        return ConfigurationMapper.fromSystemConfiguration(this, qualifier);
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public void setWebDriverConfiguration(TypedWebDriverConfiguration webDriverConfiguration) {
        this.webDriverConfiguration = webDriverConfiguration;
    }

    public TypedWebDriverConfiguration getWebDriverConfiguration() {
        return webDriverConfiguration;
    }

    public void setSeleniumConfiguration(SeleniumConfiguration seleniumConfiguration) {
        this.seleniumConfiguration = seleniumConfiguration;
    }

}

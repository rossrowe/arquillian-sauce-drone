package com.saucelabs.drone.webdriver;

import com.saucelabs.drone.SauceConfiguration;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.drone.configuration.ConfigurationMapper;
import org.jboss.arquillian.drone.spi.DroneConfiguration;

import java.lang.annotation.Annotation;

/**
 * @author Ross Rowe
 */
public class WebDriverConfiguration extends SauceConfiguration implements DroneConfiguration<WebDriverConfiguration> {

    private int serverPort = 80;

    public static final String CONFIGURATION_NAME = "sauce-webdriver";

    public String getConfigurationName() {
        return CONFIGURATION_NAME;
    }

    public WebDriverConfiguration configure(ArquillianDescriptor descriptor, Class<? extends Annotation> qualifier) {
        ConfigurationMapper.fromArquillianDescriptor(descriptor, this, qualifier);
        return ConfigurationMapper.fromSystemConfiguration(this, qualifier);
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

}

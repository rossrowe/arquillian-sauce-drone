package com.saucelabs.drone.selenium;

import com.saucelabs.drone.SauceConfiguration;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.drone.configuration.ConfigurationMapper;
import org.jboss.arquillian.drone.spi.DroneConfiguration;

import java.lang.annotation.Annotation;

/**
 * Need to support parameters defined in arq.xml as well as from 'sauce-ondemand:' string.
 * @author Ross Rowe
 */
public class SauceSeleniumConfiguration extends SauceConfiguration
        implements DroneConfiguration<SauceSeleniumConfiguration> {

    public static final String CONFIGURATION_NAME = "sauce-selenium";

    private int serverPort = 4444;

    public String getConfigurationName() {
        return CONFIGURATION_NAME;
    }

    public SauceSeleniumConfiguration configure(ArquillianDescriptor descriptor, Class<? extends Annotation> qualifier) {
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

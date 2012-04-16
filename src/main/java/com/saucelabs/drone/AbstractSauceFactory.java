package com.saucelabs.drone;

/**
 * @author Ross Rowe
 */
public abstract class AbstractSauceFactory {

    protected static final String FACTORY_URL = "sauce-ondemand:username={0}&access-key={1}&max-duration={2}&os={3}&browser={4}&version={5}";

    protected String readPropertyOrEnv(String key) {
        String v = System.getProperty(key);
        if (v == null)
            v = System.getenv(key);

        return v;
    }
}

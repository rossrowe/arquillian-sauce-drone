package com.saucelabs.drone;

/**
 * @author Ross Rowe
 */
public abstract class AbstractSauceFactory {

    protected static final String URL_FORMAT = "http://{0}:{1}@{2}:{3}/wd/hub";

    protected String readPropertyOrEnv(String key, String defaultValue) {
        String v = System.getProperty(key);
        if (v == null)
            v = System.getenv(key);

        if (v == null) {
            v = defaultValue;
        }
        return v;
    }
}

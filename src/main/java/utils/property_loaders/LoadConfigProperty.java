package utils.property_loaders;

import utils.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Load config property file, use singleton
 * @author Olha Yuryeva
 * @version 1.0
 */

public class LoadConfigProperty {

    private LoadConfigProperty() {}

    private static class LoadConfigPropertyHolder {
        private final static LoadConfigProperty instance = new LoadConfigProperty();
    }

    public static LoadConfigProperty getInstance() {
        return LoadConfigPropertyHolder.instance;
    }

    /**
     * @param value String
     * @return String property
     */
    public String getConfigProperty(String value) {
        Properties property = new Properties();
        try (InputStream is = this.getClass().getClassLoader().
                getResourceAsStream(Strings.CONFIG_PROPERTIES)){
            property.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property.getProperty(value);
    }
}

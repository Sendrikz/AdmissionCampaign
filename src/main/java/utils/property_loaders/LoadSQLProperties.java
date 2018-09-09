package utils.property_loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Load sql property file, use singleton
 * @author Olha Yuryeva
 * @version 1.0
 */

public class LoadSQLProperties {

    private LoadSQLProperties() {}

    private static class LoadSQLPropertiesHolder {
        private final static LoadSQLProperties instance = new LoadSQLProperties();
    }

    public static LoadSQLProperties getInstance() {
        return LoadSQLProperties.LoadSQLPropertiesHolder.instance;
    }

    /**
     * @param resource String
     * @param value String
     * @return String property
     */
    public String getConfigProperty(String resource, String value) {
        Properties property = new Properties();
        try (InputStream is = this.getClass().getClassLoader().
                getResourceAsStream(resource)){
            property.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property.getProperty(value);
    }
}

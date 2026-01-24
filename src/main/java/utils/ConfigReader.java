package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop;

    private ConfigReader() {}

    public static Properties getProperties() {
        if (prop == null) {
            prop = new Properties();
            try (InputStream is = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties")) {

                if (is == null) {
                    throw new RuntimeException("config.properties not found in classpath");
                }
                prop.load(is);

            } catch (Exception e) {
                throw new RuntimeException("Failed to load config.properties", e);
            }
        }
        return prop;
    }

    public static String get(String key) {
    	return System.getProperty(key,
                getProperties().getProperty(key));
    }
}

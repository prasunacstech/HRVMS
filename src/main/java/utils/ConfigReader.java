package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop;

    private ConfigReader() {}

    public static Properties getProperties() {
        if (prop == null) {
            prop = new Properties();
            String env = System.getProperty("env", "qa"); // default QA

            String filePath =
                    "src/test/resources/config-" + env + ".properties";
            try (FileInputStream fis = new FileInputStream(filePath)) {
                prop.load(fis);
            } catch (IOException e) {
                throw new RuntimeException(
                        "Failed to load config file for env: " + env, e
                );
            }
        }
        return prop;
    }

    public static String get(String key) {
        String value = getProperties().getProperty(key);
        if (value == null) {
            throw new RuntimeException(
                key + " is not defined in config file"
            );
        }
        return value;
    }
}

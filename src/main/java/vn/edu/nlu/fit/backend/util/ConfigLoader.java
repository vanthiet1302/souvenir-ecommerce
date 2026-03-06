package vn.edu.nlu.fit.backend.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    public static Properties load() throws IOException {
        Properties prop = new Properties();

        InputStream is = ConfigLoader.class
                .getClassLoader()
                .getResourceAsStream("config.properties");

        prop.load(is);
        return prop;
    }

    public static void main(String[] args) throws IOException {
        Properties prop = ConfigLoader.load();

    }
}

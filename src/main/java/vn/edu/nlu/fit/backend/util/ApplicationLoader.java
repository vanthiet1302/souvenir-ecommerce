package vn.edu.nlu.fit.backend.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ApplicationLoader {
    public static Properties load() throws IOException {
        Properties prop = new Properties();

        InputStream is = ApplicationLoader.class
                .getClassLoader()
                .getResourceAsStream("application.properties");

        prop.load(is);
        return prop;
    }

    public static void main(String[] args) throws IOException, SQLException {
        Properties prop = ApplicationLoader.load();
        String dbUrl = prop.getProperty("db.url");
        String dbUser = prop.getProperty("db.user");
        String dbPassword = prop.getProperty("db.password");
        System.out.println(dbUrl);
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        if (conn != null){
            System.out.println("Connected successfully to MySQL");
        } else {
            System.out.println("Failed.");
        }
    }
}

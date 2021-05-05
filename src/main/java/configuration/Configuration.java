package configuration;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    public static String URL;
    public static String BASIC_AUTH;
    public static String USERNAME;
    public static String PASSWORD;
    private static final Properties properties = new Properties();

    @SneakyThrows
    public static void load() {
        InputStream inputStream = new FileInputStream("src/main/resources/app.properties");
        properties.load(inputStream);
        URL = System.getProperty("url", properties.getProperty("url"));
        BASIC_AUTH = System.getProperty("basic.auth", properties.getProperty("basic.auth"));
        USERNAME = properties.getProperty("username");
        PASSWORD = properties.getProperty("password");
    }

    public static String getURL() {
        return URL;
    }

    public static String getBasicAuth() {
        return BASIC_AUTH;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }

}

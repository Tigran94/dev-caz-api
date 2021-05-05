package utils;

import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    @SneakyThrows
    private static String generateStringFromResource(final String path) {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    @SneakyThrows
    public JSONObject generateJSONObjectFromResource(final String path) {
        final String jsonString = generateStringFromResource(path);
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(jsonString);
    }
}

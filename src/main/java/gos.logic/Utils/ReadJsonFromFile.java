package gos.logic.Utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ReadJsonFromFile {
    private final static JSONParser parser = new JSONParser();

    private ReadJsonFromFile() {
    }

    public static JSONObject readAsJsonObject(String fileName) {
        return (JSONObject) readJson(fileName);
    }

    public static JSONArray readAsJsonArray(String fileName) {
        return (JSONArray) readJson(fileName);
    }

    private static Object readJson(String fileName) {
        ClassLoader classLoader = ReadJsonFromFile.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        checkInputStreamForNull(inputStream);

        Object object = null;
        try {
            object = parser.parse(new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return object;
    }

    private static void checkInputStreamForNull(InputStream inputStream) {
        if (inputStream == null)
            throw new IllegalArgumentException("file not found!");
    }
}

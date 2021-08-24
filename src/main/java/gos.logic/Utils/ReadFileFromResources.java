package gos.logic.Utils;

import java.io.InputStream;

public class ReadFileFromResources {
    private ReadFileFromResources() {
    }

    public static InputStream readFile(String fileName) {
        ClassLoader classLoader = ReadJsonFromFile.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
}

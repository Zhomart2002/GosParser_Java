package gos.logic.ClientUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResponseManager {

    private static final JSONParser parser = new JSONParser();

    private ResponseManager() {
    }

    protected static JSONObject getResponseAsJsonObject(HttpResponse response) throws IOException {
        try {

            HttpEntity input = response.getEntity();

            if (input == null)
                throw new IOException("Response doesn't have body");

            return (JSONObject) parser.parse(EntityUtils.toString(input, StandardCharsets.UTF_8));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

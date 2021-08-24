package gos.logic.ClientUtil;

import gos.logic.Utils.QueryUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class Client {

    private final String token;
    private HttpClient httpClient;
    private HttpPost httpPost;
    private HttpResponse response;

    protected Client(String token) {
        this.token = token;
        try {
            createHttpClient();
            createHttpPostAndSetUp();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private void createHttpClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        httpClient = HttpClients
                .custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustSelfSignedStrategy.INSTANCE).build())
                .build();
    }

    private void createHttpPostAndSetUp() {
        httpPost = new HttpPost(QueryUtils.getUrl());
        httpPost.setHeader("Authorization", "Bearer " + this.token);
        httpPost.setHeader("Content-type", "text/plain");
    }

    public JSONObject postAndGetJsonObject(String json) throws IOException {
        this.post(json);
        return ResponseManager.getResponseAsJsonObject(this.response);
    }

    private void post(String json) throws IOException {
        httpPost.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
        response = httpClient.execute(httpPost);
    }
}

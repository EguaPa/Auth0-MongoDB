package service;

import com.google.gson.Gson;
import model.User;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;

public class Auth0ServiceImpl implements Auth0Service {
    private static final Logger logger = LogManager.getLogger(Auth0ServiceImpl.class);
    private final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private final Gson gson = new Gson();

    @Override
    public String getToken() {
        logger.info("Getting access token from Auth0...");

        MediaType json = MediaType.parse("application/json");
        RequestBody tokenRequestBody = RequestBody.create(json, "{\r\n    \"client_id\": \"sdkQ0SknXwjH3cpf2iM60PC5Q0j1hRuO\",\r\n    \"client_secret\": \"3CvPfrys2JiFa_S1iDAh6cBfmPGPCRwXZMjWpJup-ifiYANwxu-rwu5qpBAPRtrT\",\r\n    \"audience\": \"https://eguapa.eu.auth0.com/api/v2/\",\r\n    \"grant_type\": \"client_credentials\"\r\n}");
        Request tokenRequest = new Request.Builder()
                .url("https://eguapa.eu.auth0.com/oauth/token")
                .method("POST", tokenRequestBody)
                .build();
        Response tokenResponse = null;
        try {
            tokenResponse = client.newCall(tokenRequest).execute();
        } catch (IOException e) {
            logger.error("Failed to fetch access token");
            throw new RuntimeException(e);
        }

        // token deserialization

        HashMap tokenResponseBody = gson.fromJson(tokenResponse.body().charStream(), HashMap.class);
        String accessToken = (String) tokenResponseBody.get("access_token");
        return accessToken;
    }

    @Override
    public User getUser(String id, String accessToken) {
        logger.info("Getting user with id: {} from Auth0...", id);
        Request userRequest = new Request.Builder()
                .url("https://eguapa.eu.auth0.com/api/v2/users/" + id)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        Response userResponse = null;
        try {
            userResponse = client.newCall(userRequest).execute();
        } catch (IOException e) {
            logger.error("Failed to fetch user with id: {}", id);
            throw new RuntimeException(e);
        }
        User user = gson.fromJson(userResponse.body().charStream(), User.class);
        logger.info("Fetched user: {}", user);
        return user;
    }
}

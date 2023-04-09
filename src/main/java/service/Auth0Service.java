package service;

import model.User;

public interface Auth0Service {
    String getToken();
    User getUser(String id, String accessToken);
}

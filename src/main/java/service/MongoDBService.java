package service;

import model.User;

public interface MongoDBService {
    void pushUser(User user);
    void deleteUser(String userId);
    User findUser(String id);
}

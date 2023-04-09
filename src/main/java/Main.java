import model.User;
import service.Auth0Service;
import service.Auth0ServiceImpl;
import service.MongoDBService;
import service.MongoDBServiceImpl;

public class Main {
    public static void main(String[] args) {
        Auth0Service auth0Service = new Auth0ServiceImpl();
        String token = auth0Service.getToken();
        User user1 = auth0Service.getUser("auth0|6431497cba0dc586d50c04af", token);
        User user2 = auth0Service.getUser("auth0|643149c59aac65f8126d2cbf", token);

        MongoDBService mongoDBService = new MongoDBServiceImpl();
        mongoDBService.pushUser(user1);
        mongoDBService.pushUser(user2);
//        mongoDBService.deleteUser("22");
//        mongoDBService.deleteUser("1");
    }
}

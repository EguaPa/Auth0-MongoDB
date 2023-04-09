package service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class MongoDBServiceImpl implements MongoDBService {
    private static final Logger logger = LogManager.getLogger(MongoDBServiceImpl.class);
    private final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    private final CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    private final CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    private final MongoDatabase database = mongoClient.getDatabase("Auth0-test").withCodecRegistry(pojoCodecRegistry);
    private final MongoCollection<User> collection = database.getCollection("users", User.class);

    @Override
    public void pushUser(User user) {
        logger.info("Saving user to local MongoDB");

        User existingUser = findUser(user.getUserId());
        if (existingUser != null){
            logger.info("User with id '{}' already exists", user.getUserId());
            return;
        }

        collection.insertOne(user);

    }

    @Override
    public void deleteUser(String userId) {
        logger.info("Deleting user with id: '{}'", userId);

        collection.deleteOne(eq("user_id", userId));
    }

    @Override
    public User findUser(String id) {
        logger.info("Searching user with id: {}", id);

        User fetchedUser = collection.find(eq("user_id", id)).first();
        if (fetchedUser != null) {
            logger.info("The following {} was found", fetchedUser);
            return fetchedUser;
        } else {
            logger.error("user with id: '{}' not found", id);
            throw new RuntimeException();
        }
    }
}

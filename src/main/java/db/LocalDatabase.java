package db;

import com.google.common.collect.Maps;

import user.User;

import java.util.Collection;
import java.util.Map;

public class LocalDatabase {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserByUserId(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}

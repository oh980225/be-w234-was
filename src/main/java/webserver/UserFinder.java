package webserver;

import db.Database;
import model.User;

import java.util.HashSet;
import java.util.Set;

public class UserFinder {
    public static Set<User> findAll() {
        return new HashSet<>(Database.findAll());
    }
}

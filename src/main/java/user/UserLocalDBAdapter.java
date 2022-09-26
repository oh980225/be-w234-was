package user;

import db.Database;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserLocalDBAdapter implements UserFindable, UserRegisterable {
    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(Database.findUserByUserId(userId));
    }

    @Override
    public Set<User> findAll() {
        return new HashSet<>(Database.findAll());
    }

    @Override
    public void register(User user) {
        Database.addUser(user);
    }
}

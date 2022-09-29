package user;

import db.LocalDatabase;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserLocalDBAdapter implements UserFindable, UserRegisterable {
    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(LocalDatabase.findUserByUserId(userId));
    }

    @Override
    public Set<User> findAll() {
        return new HashSet<>(LocalDatabase.findAll());
    }

    @Override
    public void register(User user) {
        LocalDatabase.addUser(user);
    }
}

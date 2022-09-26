package user;

import java.util.Optional;
import java.util.Set;

public interface UserFindable {
    Optional<User> findByUserId(String userId);
    Set<User> findAll();
}

package user;

import java.util.Optional;

public interface UserFindable {
    Optional<User> findByUserId(String userId);
}

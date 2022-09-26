package user;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class UserFinder {
    private final UserFindable userFindable;

    public Set<User> findAll() {
        return userFindable.findAll();
    }
}

package user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserFinderTest {
    private int findAllCallCnt = 0;

    @Test
    @DisplayName("UserFindable를 이용하여 모든 사용자를 조회한다.")
    void findAll() {
        var userFinder = new UserFinder(new UserFindable() {
            @Override
            public Optional<User> findByUserId(String userId) {
                return Optional.empty();
            }

            @Override
            public Set<User> findAll() {
                findAllCallCnt++;
                return null;
            }
        });

        userFinder.findAll();

        assertThat(findAllCallCnt).isOne();
    }
}
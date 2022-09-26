package user;

import db.Database;
import user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import user.UserFinder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserFinderTest {
    @Test
    @DisplayName("모든 사용자를 찾는다.")
    void findAll() {
        var user1 = User.builder()
                .userId("george.5")
                .password("password456")
                .name("오승재")
                .email("oh980225@naver.com")
                .build();
        var user2 = User.builder()
                .userId("oh980225")
                .password("password123")
                .name("오승재")
                .email("oh980225@gmail.com")
                .build();
        Database.addUser(user1);
        Database.addUser(user2);

        var actual = UserFinder.findAll();

        assertThat(actual).isEqualTo(Set.of(user1, user2));
    }
}
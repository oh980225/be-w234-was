package webserver;

import db.Database;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserAuthProviderTest {
    @Test
    @DisplayName("회원가입을 진행한다.")
    void signUp() {
        var request = SignUpRequest.builder()
                .userId("george.5")
                .password("password123")
                .name("오승재")
                .email("oh980225@gmail.com")
                .build();

        UserAuthProvider.signUp(request);

        assertThat(Database.findUserById("george.5"))
                .isEqualTo(User.builder()
                        .userId("george.5")
                        .password("password123")
                        .name("오승재")
                        .email("oh980225@gmail.com")
                        .build());
    }
}
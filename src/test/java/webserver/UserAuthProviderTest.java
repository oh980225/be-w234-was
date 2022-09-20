package webserver;

import db.Database;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("입력으로 들어온 userId가 이미 존재하여 중복될 경우, UserException이 발생합니다.")
    void signUp_duplicate_user_id() {
        Database.addUser(User.builder()
                .userId("george.5")
                .password("password456")
                .name("오승재")
                .email("oh980225@naver.com")
                .build());
        var request = SignUpRequest.builder()
                .userId("george.5")
                .password("password123")
                .name("오승재1")
                .email("oh980225@gmail.com")
                .build();

        assertThatThrownBy(() -> UserAuthProvider.signUp(request))
                .isInstanceOf(UserException.class)
                .hasMessage(UserErrorMessage.DUPLICATE_USER_ID.getDetail());
    }
}
package webserver;

import db.Database;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserAuthProviderTest {
    @BeforeEach
    void init() {
        Database.cleanUp();
    }

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

        assertThat(Database.findUserById("george.5").get())
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

    // login에 대한 positive 테스트를 작성하고 싶은데 반환값도 void라 현 상황에서는 쉽지 않네요 🧐
    // Database 클래스를 좀 건드린 후, 인터페이스를 뽑고, 테스트용 Mock 구현체를 만들어서 호출을 검증하는 정도를 고민 중입니다...
    @Test
    @DisplayName("userId와 password로 로그인을 할 수 있습니다.")
    void login() {
        Database.addUser(User.builder()
                .userId("george.5")
                .password("password123")
                .name("오승재")
                .email("oh980225@naver.com")
                .build());
        var request = new LoginRequest("george.5", "password123");

        UserAuthProvider.login(request);
    }

    @Test
    @DisplayName("로그인하려는 사용자가 존재하지 않는 경우, UserException이 발생합니다.")
    void login_not_exist_user() {
        var request = new LoginRequest("george.5", "password123");

        assertThatThrownBy(() -> UserAuthProvider.login(request))
                .isInstanceOf(UserException.class)
                .hasMessage(UserErrorMessage.NOT_EXIST_USER.getDetail());
    }

    @Test
    @DisplayName("로그인 시도시 비밀번호가 틀렸을 경우, UserException이 발생합니다.")
    void login_not_match_password() {
        Database.addUser(User.builder()
                .userId("george.5")
                .password("password456")
                .name("오승재")
                .email("oh980225@naver.com")
                .build());

        var request = new LoginRequest("george.5", "password123");

        assertThatThrownBy(() -> UserAuthProvider.login(request))
                .isInstanceOf(UserException.class)
                .hasMessage(UserErrorMessage.NOT_MATCH_PASSWORD.getDetail());
    }
}
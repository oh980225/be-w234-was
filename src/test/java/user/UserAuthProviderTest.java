package user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserAuthProviderTest {
    private int findByUserIdCallCnt;
    private int registerCallCnt;

    @BeforeEach
    void init() {
        findByUserIdCallCnt = 0;
        registerCallCnt = 0;
    }

    @Test
    @DisplayName("UserFindable로 중복된 아이디의 유저가 없는지 확인후, UserRegisterable로 유저 등록을 진행합니다.")
    void signUp() {
        var userAuthProvider = getAuthProviderForEmptyUser();
        var request = SignUpRequest.builder()
                .userId("george.5")
                .password("password123")
                .name("오승재")
                .email("oh980225@gmail.com")
                .build();

        userAuthProvider.signUp(request);

        assertThat(findByUserIdCallCnt).isOne();
        assertThat(registerCallCnt).isOne();
    }

    @Test
    @DisplayName("UserFindable로 입력으로 들어온 userId에 해당한 유저를 찾았을 경우 중복이므로, UserException이 발생합니다.")
    void signUp_duplicate_user_id() {
        var userAuthProvider = getUserAuthProvider();
        var request = SignUpRequest.builder()
                .userId("george.5")
                .password("password123")
                .name("오승재1")
                .email("oh980225@gmail.com")
                .build();

        assertThatThrownBy(() -> userAuthProvider.signUp(request))
                .isInstanceOf(UserException.class)
                .hasMessage(UserErrorMessage.DUPLICATE_USER_ID.getDetail());
    }


    @Test
    @DisplayName("UserFindable로 userId에 해당하는 유저를 찾고 비밀번호를 비교하여 로그인을 진행합니다.")
    void login() {
        var userAuthProvider = getUserAuthProvider();
        var request = new LoginRequest("george.5", "password123");

        userAuthProvider.login(request);

        assertThat(findByUserIdCallCnt).isOne();
    }

    @Test
    @DisplayName("로그인하려는 사용자가 존재하지 않는 경우, UserException이 발생합니다.")
    void login_not_exist_user() {
        var userAuthProvider = getAuthProviderForEmptyUser();
        var request = new LoginRequest("george.5", "password123");

        assertThatThrownBy(() -> userAuthProvider.login(request))
                .isInstanceOf(UserException.class)
                .hasMessage(UserErrorMessage.NOT_EXIST_USER.getDetail());
    }

    @Test
    @DisplayName("로그인 시도시 비밀번호가 틀렸을 경우, UserException이 발생합니다.")
    void login_not_match_password() {
        var userAuthProvider = getUserAuthProvider();
        var request = new LoginRequest("george.5", "password456");

        assertThatThrownBy(() -> userAuthProvider.login(request))
                .isInstanceOf(UserException.class)
                .hasMessage(UserErrorMessage.NOT_MATCH_PASSWORD.getDetail());
    }

    private UserAuthProvider getUserAuthProvider() {
        return new UserAuthProvider(new UserFindable() {
            @Override
            public Optional<User> findByUserId(String userId) {
                findByUserIdCallCnt++;
                return Optional.of(User.builder()
                        .userId("george.5")
                        .password("password123")
                        .name("오승재")
                        .email("oh980225@naver.com")
                        .build());
            }

            @Override
            public Set<User> findAll() {
                return null;
            }
        }, (userId) -> registerCallCnt++);
    }

    private UserAuthProvider getAuthProviderForEmptyUser() {
        return new UserAuthProvider(new UserFindable() {
            @Override
            public Optional<User> findByUserId(String userId) {
                findByUserIdCallCnt++;
                return Optional.empty();
            }

            @Override
            public Set<User> findAll() {
                return null;
            }
        }, (userId) -> registerCallCnt++);
    }
}
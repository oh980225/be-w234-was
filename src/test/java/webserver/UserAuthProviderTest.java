package webserver;

import db.Database;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserAuthProviderTest {
    @Test
    @DisplayName("GET으로 회원가입을 진행한다.")
    void signUpForGet() {
//        var request = new Request(new RequestStartLine("GET /user/create?userId=george.5&password=password&name=오승재&email=oh980225@gmail.com HTTP/1.1"));
//
//        UserAuthProvider.signUpForGet(request);
//
//        assertThat(Database.findUserById("george.5"))
//                .isEqualTo(new User(
//                        "george.5",
//                        "password",
//                        "오승재",
//                        "oh980225@gmail.com"));
    }
}
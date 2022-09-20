package webserver;

import db.Database;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserAuthProviderTest {
    @Test
    @DisplayName("GET으로 회원가입을 진행한다.")
    void signUpForGet() {
        var request = new Request(new RequestStartLine("GET /user/create?userId=george.5&password=password&name=오승재&email=oh980225@gmail.com HTTP/1.1"),
                new RequestHeader(Collections.emptyMap()),
                Collections.emptyMap());

        UserAuthProvider.signUpForGet(request);

        assertThat(Database.findUserById("george.5"))
                .isEqualTo(new User(
                        "george.5",
                        "password",
                        "오승재",
                        "oh980225@gmail.com"));
    }

    @Test
    @DisplayName("POST로 회원가입을 진행한다.")
    void signUp() {
        var request = new Request(new RequestStartLine("POST /user/create HTTP/1.1"),
                new RequestHeader(Map.of("Content-Length", "93")),
                getBody());

        UserAuthProvider.signUp(request);

        assertThat(Database.findUserById("george.5"))
                .isEqualTo(new User(
                        "george.5",
                        "password",
                        "오승재",
                        "oh980225@gmail.com"));
    }

    private Map<String, String> getBody() {
        Map<String, String> body = new HashMap<>();
        body.put("userId", "george.5");
        body.put("password", "password");
        body.put("name", "오승재");
        body.put("email", "oh980225@gmail.com");
        return body;
    }
}
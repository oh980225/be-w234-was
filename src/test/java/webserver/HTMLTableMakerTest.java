package webserver;

import user.User;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HTMLTableMakerTest {
    private static final String EMPTY_TABLE_PATH = "src/test/java/static/test_empty_table.html";
    private static final String USER_TABLE_PATH = "src/test/java/static/test_user_table.html";
    private static final String UNEXPECTED_HTML_PATH = "src/test/java/static/test.html";


    @Test
    @DisplayName("사용자 목록을 html 파일 내 테이블에 삽입할 수 있습니다.")
    void makeUserTable() throws IOException {
        var user1 = User.builder()
                .userId("george.5")
                .password("password456")
                .name("오승재")
                .email("oh980225@gmail.com")
                .build();
        var user2 = User.builder()
                .userId("oh980225")
                .password("password123")
                .name("승재")
                .email("oh980225@naver.com")
                .build();

        var actual = HTMLTableMaker.makeUserTable(EMPTY_TABLE_PATH, List.of(user1, user2));

        assertThat(actual).isEqualTo(Jsoup.parse(new File(USER_TABLE_PATH), "UTF-8").html());
    }

    @Test
    @DisplayName("해당하는 경로의 파일이 지원하는 형식(table 클래스를 가진 요소가 있고, 요소 아래에 tbody태그가 존재해야합니다.)의 html파일이 아닐경우, 예외를 발생시킵니다.")
    void makeUserTable_no_table() {
        var user1 = User.builder()
                .userId("george.5")
                .password("password456")
                .name("오승재")
                .email("oh980225@gmail.com")
                .build();
        var user2 = User.builder()
                .userId("oh980225")
                .password("password123")
                .name("승재")
                .email("oh980225@naver.com")
                .build();

        assertThatThrownBy(() -> HTMLTableMaker.makeUserTable(UNEXPECTED_HTML_PATH, List.of(user1, user2)))
                .isInstanceOf(WebServerException.class)
                .hasMessage(WebServerErrorMessage.UNEXPECTED_HTML.getDetail());
    }
}
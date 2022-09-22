package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PageLoaderTest {

    private static final String TEST_STATIC_PATH = "src/test/java/static/test.html";

    @Test
    @DisplayName("주어진 경로에 해당하는 static파일을 반환합니다.")
    void getPageByPath() {
        assertThat(PageLoader.getPageByPath(TEST_STATIC_PATH)).isEqualTo(new File(TEST_STATIC_PATH));
    }

    @Test
    @DisplayName("해당 경로에 찾는 파일이 없을 경우, WebServerException이 발생합니다.")
    void getPageByPath_not_found() {
        assertThatThrownBy(() -> PageLoader.getPageByPath("src/test/java/static/not_found.html"))
                .isInstanceOf(WebServerException.class)
                .hasMessage(WebServerErrorMessage.NOT_FOUND.getDetail());
    }
}
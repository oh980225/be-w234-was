package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeTest {

    @Test
    @DisplayName("경로 내의 확장자를 지원할 수 있는 ContentType을 찾아줍니다.")
    void findByPath() {
        var contentType = ContentType.findByPath("./css/style.css");

        assertThat(contentType).isEqualTo(ContentType.TEXT_CSS);
    }

    @Test
    @DisplayName("ContentType의 기본값은 'text/html'로 합니다.")
    void findByPath_default_HTML() {
        var contentType = ContentType.findByPath("/index.html");

        assertThat(contentType).isEqualTo(ContentType.TEXT_HTML);
    }
}
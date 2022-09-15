package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTypeTest {

    @Test
    @DisplayName("확장자를 지원할 수 있는 ContentType을 찾아줍니다.")
    void findByExtension() {
        var contentType = ContentType.findByExtension(new Extension("./css/style.css"));

        assertThat(contentType).isEqualTo(ContentType.TEXT_CSS);
    }

    @Test
    @DisplayName("서비스가 지원하는 ContentType의 기본값은 text/html로 합니다.")
    void findByExtension_invalid_contentType() {
        var contentType = ContentType.findByExtension(new Extension("./images/home.png"));

        assertThat(contentType).isEqualTo(ContentType.TEXT_HTML);
    }
}
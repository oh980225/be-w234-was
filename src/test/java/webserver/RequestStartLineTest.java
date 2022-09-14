package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestStartLineTest {
    @Test
    @DisplayName("요청 헤더의 값은 null일 수 없습니다.")
    void constructor_null() {
        assertThatThrownBy(() -> new RequestStartLine(null))
                .isInstanceOf(WebServerException.class)
                .hasMessage(WebServerErrorMessage.EMPTY_REQUEST.getDetail());
    }

    @Test
    @DisplayName("요청 헤더의 값은 빈 문자열일 수 없습니다.")
    void constructor_empty() {
        assertThatThrownBy(() -> new RequestStartLine(""))
                .isInstanceOf(WebServerException.class)
                .hasMessage(WebServerErrorMessage.EMPTY_REQUEST.getDetail());
    }

    @Test
    @DisplayName("요청 헤더의 첫 번째 라인은 '메서드 경로 프로토콜' 형식이어야합니다.")
    void constructor_invalid_format() {
        assertThatThrownBy(() -> new RequestStartLine("GET GET / HTTP/1.1"))
                .isInstanceOf(WebServerException.class)
                .hasMessage(WebServerErrorMessage.INVALID_FORMAT.getDetail());
    }
}
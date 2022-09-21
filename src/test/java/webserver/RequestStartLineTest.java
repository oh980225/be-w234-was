package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestStartLineTest {
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("요청 헤더의 값은 null이거나 비어있을 수 없습니다.")
    void constructor_null_or_empty(String startLine) {
        assertThatThrownBy(() -> new RequestStartLine(startLine))
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
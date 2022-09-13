package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestHeaderLineTest {
    @Test
    @DisplayName("요청 헤더의 값은 null일 수 없습니다.")
    void constructor_null() {
        assertThatThrownBy(() -> new RequestHeaderLine(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 요청입니다.");
    }

    @Test
    @DisplayName("요청 헤더의 값은 빈 문자열일 수 없습니다.")
    void constructor_empty() {
        assertThatThrownBy(() -> new RequestHeaderLine(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 요청입니다.");
    }

    @Test
    @DisplayName("요청 헤더의 첫 번째 라인은 '메서드 경로 프로토콜' 형식이어야합니다.")
    void constructor_invalid_format() {
        assertThatThrownBy(() -> new RequestHeaderLine("GET GET / HTTP/1.1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("형식이 일치하지 않습니다.");
    }
}
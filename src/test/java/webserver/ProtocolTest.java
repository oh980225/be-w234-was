package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtocolTest {
    @Test
    @DisplayName("HTTP/1.1은 지원하고 있습니다.")
    void findByValue_HTTP_1_1() {
        assertThat(Protocol.findByName("HTTP/1.1")).isEqualTo(Protocol.HTTP_1_1);
    }

    @Test
    @DisplayName("TCP는 지원하고 있지 않습니다.")
    void findByValue_not_match_TCP() {
        assertThatThrownBy(() -> Protocol.findByName("TCP"))
                .isInstanceOf(WebServerException.class)
                .hasMessage(WebServerErrorMessage.INVALID_PROTOCOL.getDetail());
    }
}
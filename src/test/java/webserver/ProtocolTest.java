package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtocolTest {

    @Test
    @DisplayName("enum으로 제공하는 프로토콜만 지원합니다.")
    void findByValue_not_match() {
        assertThatThrownBy(() -> Protocol.findByValue("TCP"))
                .isInstanceOf(WebServerException.class)
                .hasMessage(WebServerErrorMessage.INVALID_PROTOCOL.getDetail());
    }
}
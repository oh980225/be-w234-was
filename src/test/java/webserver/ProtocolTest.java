package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtocolTest {
    @ParameterizedTest
    @EnumSource(Protocol.class)
    @DisplayName("Enum으로 제공하고 있는 프로토콜들을 지원하고 있습니다.")
    void findByValue_HTTP_1_1(Protocol protocol) {
        assertThat(Protocol.findByName(protocol.getName())).isEqualTo(protocol);
    }

    @Test
    @DisplayName("TCP는 지원하고 있지 않습니다.")
    void findByValue_not_match_TCP() {
        assertThatThrownBy(() -> Protocol.findByName("TCP"))
                .isInstanceOf(WebServerException.class)
                .hasMessage(WebServerErrorMessage.INVALID_PROTOCOL.getDetail());
    }
}
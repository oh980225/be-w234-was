package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseStatusLineTest {

    @Test
    @DisplayName("Http Response의 StatusLine 형식으로 출력된다.")
    void testToString() {
        assertThat(new ResponseStatusLine(Protocol.HTTP_1_1, StatusCode.OK).toString()).isEqualTo("HTTP/1.1 200 OK");
    }
}
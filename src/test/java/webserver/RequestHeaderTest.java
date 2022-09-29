package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {

    @Test
    @DisplayName("요청 헤더의 Content-Length값을 정수로 리턴합니다.")
    void getContentLength() {
        var requestHeader = new RequestHeader();
        requestHeader.putContent("Content-Length", "95");
        assertThat(requestHeader.getContentLength()).isEqualTo(95);
    }

    @Test
    @DisplayName("요청 헤더의 Content-Length값이 없을 경우, 0을 리턴합니다.")
    void getContentLength_not_exist_content_length() {
        var requestHeader = new RequestHeader();
        assertThat(requestHeader.getContentLength()).isEqualTo(0);
    }
}
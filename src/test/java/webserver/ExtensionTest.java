package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExtensionTest {
    @Test
    @DisplayName("확장자가 없는 경로를 이용해서는 Extension객체를 만들 수 없습니다.")
    void constructor_not_exist_extension() {
        assertThatThrownBy(() -> new Extension("./css/style"))
                .isInstanceOf(WebServerException.class)
                .hasMessage(WebServerErrorMessage.NOT_EXIST_EXTENSION.getDetail());
    }
}
package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMappingTest {

    @Test
    @DisplayName("등록된 url 요청이 아닐 경우, 페이지 로드 요청으로 판단한다.")
    void valueOf_page_load() {
        var actual = RequestMapping.findBy(HttpMethod.GET, "/index.html");
        assertThat(actual).isEqualTo(RequestMapping.PAGE_LOAD);
    }

    @Test
    @DisplayName("등록된 url 요청일 경우, 해당 값과 매핑한다.")
    void valueOf_request_mapping() {
        var actual = RequestMapping.findBy(HttpMethod.GET, "/user/create");
        assertThat(actual).isEqualTo(RequestMapping.GET_SIGN_UP);
    }
}
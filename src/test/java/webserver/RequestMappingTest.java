package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestMappingTest {

    @Test
    void valueOf_page_load() {
        var actual = RequestMapping.valueOf(HttpMethod.GET, "/index.html");
        assertThat(actual).isEqualTo(RequestMapping.PAGE_LOAD);
    }

    @Test
    void valueOf_request_mapping() {
        var actual = RequestMapping.valueOf(HttpMethod.GET, "/user/create");
        assertThat(actual).isEqualTo(RequestMapping.GET_SIGN_UP);
    }
}
package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UrlTest {
    @Test
    void constructor_no_query() {
        var url = new Url("/user");

        assertThat(url.getQuery().size()).isZero();
    }

    @Test
    void constructor_with_query() {
        var url = new Url("/user?name=george.5");

        assertThat(url.getQuery().get("name")).isEqualTo("george.5");
    }
}
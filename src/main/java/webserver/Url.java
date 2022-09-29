package webserver;

import lombok.Getter;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Getter
public class Url {
    private final String path;
    private final Map<String, String> query;

    public Url(String url) {
        String[] splitUrl = URLDecoder.decode(url, UTF_8).split("\\?");
        this.path = splitUrl[0];

        if (hasQueryParam(splitUrl)) {
            this.query = HttpRequestUtils.parseQueryString(splitUrl[1]);
            return;
        }

        this.query = new HashMap<>();
    }

    private boolean hasQueryParam(String[] splitUrl) {
        return splitUrl.length == 2;
    }
}

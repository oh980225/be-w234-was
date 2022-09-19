package webserver;

import lombok.Getter;
import util.HttpRequestUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Url {
    private final String path;
    private final Map<String, String> query;

    public Url(String url) {
        String[] splitUrl = url.split("\\?");
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

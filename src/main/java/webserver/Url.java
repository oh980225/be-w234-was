package webserver;

import util.HttpRequestUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Url {
    public Url(String url) {
        String[] splitUrl = url.split("\\?");
        this.path = splitUrl[0];

        if (splitUrl.length == 2) {
            this.query = HttpRequestUtils.parseQueryString(splitUrl[1]);
            return;
        }

        this.query = new HashMap<>();
    }

    private final String path;
    private final Map<String, String> query;

    public String getPath() {
        return path;
    }

    public Map<String, String> getQuery() {
        return query;
    }
}

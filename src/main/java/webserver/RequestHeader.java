package webserver;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private final Map<String, String> contents = new HashMap<>();
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String COOKIE = "Cookie";

    public void putContent(String key, String value) {
        contents.put(key, value);
    }

    public int getContentLength() {
        var length = contents.get(CONTENT_LENGTH);
        return length == null ? 0 : Integer.parseInt(length);
    }

    public Cookie getCookie() {
        return new Cookie(HttpRequestUtils.parseCookies(contents.get(COOKIE)));
    }
}

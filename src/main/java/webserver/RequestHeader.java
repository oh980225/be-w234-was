package webserver;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class RequestHeader {
    private final Map<String, String> contents;
    private final String CONTENT_LENGTH = "Content-Length";

    private final String COOKIE = "Cookie";


    public int getContentLength() {
        var length = contents.get(CONTENT_LENGTH);
        return length == null ? 0 : Integer.parseInt(length);
    }

    public Cookie getCookie() {
        return new Cookie(HttpRequestUtils.parseCookies(contents.get(COOKIE)));
    }
}
